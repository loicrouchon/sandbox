///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.jfree:jfreechart:1.5.2

import java.awt.BasicStroke;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiFunction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

class invest {

    public static void main(String... args) {
        new invest().execute();
    }

    private void execute() {
        Period period = new Period(10);
        SharePrice initialSharePrice = new SharePrice(100d);
        List<Fund> funds = new ArrayList<>();
        for (double depositFees : new double[]{3.6d}) {
            for (double adminFees : new double[]{0.28d}) {
                for (double averageYearlyPerformance : new double[]{7d}) {
                    for (long seed = 0L; seed < 5000L; seed++) {
                        Conditions conditions = new Conditions(period, initialSharePrice,
                                250d, Percent.ofPercentage(2d),
                                Percent.ofPercentage(depositFees), Percent.ofPercentage(adminFees),
                                Percent.ofPercentage(averageYearlyPerformance), Percent.ofPercentage(5d),
                                seed, Percent.ofPercentage(1d));
                        Fund fund = new Fund(conditions);
                        funds.add(fund);
                        for (int i = 0; i <= conditions.period.duration(); i++) {
                            fund.invest(i);
                        }
                    }
                }
            }
        }

        //        List<Fund> fundsToDisplay = funds;
        List<Fund> fundsToDisplay = new ArrayList<>();
        funds.sort(Comparator.comparing(fund -> fund.reports.get(fund.reports.size() - 1).gains().percentage()));
        for (int percentile : new int[]{5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 95}) {
            int percentileIndex = (int) Math.min(funds.size() - 1, (double) percentile / 100d * funds.size());
            Fund fund = funds.get(percentileIndex);
            fund.title("percentile-" + percentile);
            fundsToDisplay.add(fund);
        }
        monthToMonthRelativePerformanceGraph(fundsToDisplay);
        cumlulatedRelativePerformanceGraph(fundsToDisplay);
        cumulatedAbsolutePerformanceGraph(fundsToDisplay);
        feesGraph(fundsToDisplay);
        //        averageFund(period, funds);
    }

    private void averageFund(Period period, List<Fund> funds) {
        List<List<MonthlyReport>> reports = funds.stream().map(fund -> fund.reports).toList();
        List<StatsReport> averageReports = new ArrayList<>();
        for (int i = 0; i < period.duration(); i++) {
            int month = i + 1;
            Statistics invested = new Statistics();
            Statistics shares = new Statistics();
            Statistics sharePrice = new Statistics();
            Statistics performance = new Statistics();
            Statistics depositFee = new Statistics();
            Statistics adminFees = new Statistics();
            for (List<MonthlyReport> report : reports) {
                MonthlyReport monthlyReport = report.get(i);
                invested.add(monthlyReport.invested());
                shares.add(monthlyReport.shares());
                sharePrice.add(monthlyReport.sharePrice());
                performance.add(monthlyReport.performance().percentage());
                depositFee.add(monthlyReport.depositFee());
                adminFees.add(monthlyReport.adminFees());
            }
            averageReports.add(new StatsReport(month, invested, shares, sharePrice,
                    performance, depositFee, adminFees));
        }
        List<Fund> averageFunds = List.of(
                statsFund(period, averageReports, 0),
                statsFund(period, averageReports, 10),
                statsFund(period, averageReports, 20),
                statsFund(period, averageReports, 30),
                statsFund(period, averageReports, 40),
                statsFund(period, averageReports, 50),
                statsFund(period, averageReports, 60),
                statsFund(period, averageReports, 70),
                statsFund(period, averageReports, 80),
                statsFund(period, averageReports, 90)
        );
        monthToMonthRelativePerformanceGraph(averageFunds);
        cumlulatedRelativePerformanceGraph(averageFunds);
        cumulatedAbsolutePerformanceGraph(averageFunds);
        feesGraph(averageFunds);
    }

    private static Fund statsFund(Period period, List<StatsReport> statsReports, int percentile) {
        Fund fund = new Fund(new Conditions(period, new SharePrice(100d),
                250d, Percent.ofPercentage(percentile),
                Percent.ofPercentage(percentile), Percent.ofPercentage(percentile),
                Percent.ofPercentage(percentile), Percent.ofPercentage(percentile),
                1L, Percent.ofPercentage(0d)));
        statsReports.stream().map(stats -> new MonthlyReport(stats.month, stats.invested().percentile(percentile),
                        stats.shares().percentile(percentile), stats.sharePrice().percentile(percentile),
                        new Percent(stats.performance().percentile(percentile)),
                        stats.depositFee().percentile(percentile), stats.adminFees().percentile(percentile)))
                .forEach(fund.reports::add);
        return fund;
    }

    record StatsReport(int month, Statistics invested, Statistics shares, Statistics sharePrice,
                       Statistics performance, Statistics depositFee, Statistics adminFees) {

    }

    static class Statistics {

        private final List<Double> values = new ArrayList<>();
        private boolean sorted;

        public void add(double value) {
            values.add(value);
            sorted = false;
        }

        public double average() {
            return values.stream().mapToDouble(Double::doubleValue).average().orElse(0d);
        }

        public double percentile(int percentile) {
            if (!sorted) {
                Collections.sort(values);
                sorted = true;
            }
            return values.get((int) Math.min((double) values.size() - 1, (double) percentile / 100d * values.size()));
        }
    }

    private static void monthToMonthRelativePerformanceGraph(List<Fund> funds) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (Fund fund : funds) {
            XYSeries sharePerformanceSeries = new XYSeries("Share Performance " + fund.title());
            XYSeries averagedPerformanceSeries = new XYSeries("Averaged Performance " + fund.title());
            double averagePerformance = 0d;
            int count = 1;
            for (MonthlyReport report : fund.reports) {
                double performance = report.performance().percentage() * 100;
                averagePerformance += performance;
                sharePerformanceSeries.add(report.month(), performance);
                averagedPerformanceSeries.add(report.month(), averagePerformance / count);
                count++;
            }
            dataset.addSeries(sharePerformanceSeries);
            dataset.addSeries(averagedPerformanceSeries);
        }
        chart(dataset, "Month to month performance", "%", "month-to-month-performance");
    }

    private static void cumlulatedRelativePerformanceGraph(List<Fund> funds) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (Fund fund : funds) {
            XYSeries gainsSeries = new XYSeries("Gains " + fund.title());
            XYSeries sharePriceSeries = new XYSeries("Share Price " + fund.title());
            for (MonthlyReport report : fund.reports) {
                gainsSeries.add(report.month(), report.gains().percentage() * 100d);
                sharePriceSeries.add(
                        report.month(), report.sharePrice() / fund.conditions.sharePrices.get(0) * 100);
            }
            dataset.addSeries(gainsSeries);
            //            dataset.addSeries(sharePriceSeries);
        }
        chart(dataset, "Relative Performance", "%", "relative-performance");
    }

    private static void cumulatedAbsolutePerformanceGraph(List<Fund> funds) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (Fund fund : funds) {
            XYSeries invested = new XYSeries("Invested " + fund.title());
            XYSeries valueSeries = new XYSeries("Value " + fund.title());
            for (MonthlyReport report : fund.reports) {
                invested.add(report.month(), report.invested());
                valueSeries.add(report.month(), report.shares * report.sharePrice());
            }
            dataset.addSeries(invested);
            dataset.addSeries(valueSeries);
        }
        chart(dataset, "Absolute Performance", "€", "absolute-performance");
    }

    private void feesGraph(List<Fund> funds) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (Fund fund : funds) {
            XYSeries depositFeesSeries = new XYSeries("Deposit Fees " + fund.title());
            XYSeries adminFeesSeries = new XYSeries("Admin Fees " + fund.title());
            XYSeries allFeesSeries = new XYSeries("All Fees " + fund.title());
            double depositFees = 0d;
            double adminFees = 0d;
            for (MonthlyReport report : fund.reports) {
                depositFees += report.depositFee();
                adminFees += report.adminFees();
                depositFeesSeries.add(report.month(), depositFees);
                adminFeesSeries.add(report.month(), adminFees);
                allFeesSeries.add(report.month(), depositFees + adminFees);
            }
            dataset.addSeries(depositFeesSeries);
            dataset.addSeries(adminFeesSeries);
            dataset.addSeries(allFeesSeries);
        }
        chart(dataset, "Fees", "€", "fees");
    }

    private static void chart(XYSeriesCollection dataset, String title, String yAxisLabel,
            String pathname) {
        JFreeChart chart = ChartFactory.createXYLineChart(
                title, "Months", yAxisLabel, dataset);
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesStroke(i, new BasicStroke(3.0f));
        }
        save(chart, pathname);
    }

    private static void save(JFreeChart chart, String pathname) {
        Path simulationOutput = Path.of("simulation");
        File file = simulationOutput.resolve(pathname + ".png").toFile();
        try {
            Files.createDirectories(simulationOutput);
            ChartUtils.saveChartAsPNG(file, chart, 1600, 1200);
        } catch (IOException e) {
            System.err.printf("Problem occurred creating chart %s%n", file);
            System.exit(1);
        }
    }

    public static class Conditions {

        private final Period period;
        private final Series<Double> investments;
        private final Series<Double> sharePrices;
        private final Percent investmentInflationCorrection;
        private final Percent depositFees;
        private final Percent adminFees;
        private final Percent averageYearlyPerformance;
        private final Percent averageMonthlyPerformance;
        private final Percent standardDeviation;
        private final long seed;
        private final Random random;
        private final Percent reinvestedDividends;
        private final Series<Percent> performance;

        public Conditions(Period period, SharePrice initialSharePrice,
                double initialInvestment, Percent investmentInflationCorrection,
                Percent depositFees, Percent adminFees,
                Percent averageYearlyPerformance, Percent standardDeviation, long seed,
                Percent reinvestedDividends) {
            this.period = period;
            this.investmentInflationCorrection = investmentInflationCorrection;
            this.depositFees = depositFees;
            this.adminFees = adminFees;
            this.averageYearlyPerformance = averageYearlyPerformance;
            this.standardDeviation = standardDeviation;
            this.seed = seed;
            this.random = new Random(seed);
            this.reinvestedDividends = reinvestedDividends;
            double targetSharePrice =
                    initialSharePrice.price() * Math.pow(1 + averageYearlyPerformance.percentage(), period.years());
            investments = period.series(0d, (monthIndex, previousValue) -> initialInvestment * Math.pow(
                    1 + this.investmentInflationCorrection.percentage(),
                    period.yearOfMonth(monthIndex)));
            averageMonthlyPerformance = new Percent(Math.pow(1 + averageYearlyPerformance.percentage(), 1d / 12d) - 1);
            //            performance = period.series(Percent.ofPercentage(0d), (monthIndex, previousValue) -> {
            //                double mean = averageMonthlyPerformance.percentage();
            //                return new Percent(mean + this.standardDeviation.percentage() * random.nextGaussian());
            //            });
            sharePrices = period.series(
                    initialSharePrice.price(),
                    (monthIndex, previousValue) -> {
                        int remainingMonths = period.duration() - monthIndex + 1;
                        //                        targetSharePrice = previousValue * Math.pow(1 + x, remainingMonths);
                        //                        Math.pow(1 + x, remainingMonths) = ;
                        //                        1 + x = Math.pow(targetSharePrice / previousValue, 1 / remainingMonths);
                        //                        x = Math.pow(targetSharePrice / previousValue, 1 / remainingMonths) - 1
                        double mean = Math.pow(targetSharePrice / previousValue, 1d / remainingMonths) - 1;
                        double perf = mean + this.standardDeviation.percentage() * random.nextGaussian();
                        double value = previousValue * (1d + perf);
                        return value;
                    });
            performance = period.series(Percent.ofPercentage(0d), (monthIndex, previousValue) ->
                    new Percent(sharePrices.get(monthIndex) / sharePrices.get(monthIndex - 1))
            );

        }
    }

    public static class Fund {

        private final Conditions conditions;
        private int month = 0;
        private double shares = 0d;
        private double invested = 0d;
        private final List<MonthlyReport> reports = new ArrayList<>();
        private String title;

        public Fund(Conditions conditions) {
            this.conditions = conditions;
        }

        public void title(String title) {
            this.title = title;
        }

        public String title() {
            return title == null ? toString() : title;
        }

        public void invest(int periodIndex) {
            month = periodIndex;
            Percent performance = conditions.performance.get(periodIndex);
            double sharePrice = conditions.sharePrices.get(periodIndex);
            double investment = conditions.investments.get(periodIndex);
            invested += investment;
            double depositFee = conditions.depositFees.of(investment);
            double valueInvested = investment - depositFee;
            double sharedBought = valueInvested / sharePrice;
            shares += sharedBought;
            double adminFeesInShares;
            if (month % 12 == 0 && month > 0) {
                adminFeesInShares = conditions.adminFees.of(shares);
                shares += conditions.reinvestedDividends.of(shares);
            } else {
                adminFeesInShares = 0d;
            }
            shares -= adminFeesInShares;
            reports.add(report(performance, sharePrice, depositFee, adminFeesInShares * sharePrice));
        }

        public MonthlyReport report(Percent performance, double sharePrice, double depositFee,
                double adminFees) {
            return new MonthlyReport(
                    month, invested, shares, sharePrice, performance, depositFee, adminFees);
        }

        @Override
        public String toString() {
            return "deposit-%.2f-admin-%.2f-perf-%.2f-seed-%d".formatted(
                    conditions.depositFees.percentage() * 100,
                    conditions.adminFees.percentage() * 100,
                    conditions.averageYearlyPerformance.percentage(), conditions.seed
            ).replace(",", ".");
        }
    }

    public record MonthlyReport(int month, double invested, double shares, double sharePrice,
                                Percent performance, double depositFee, double adminFees) {

        public Percent gains() {
            return new Percent(shares * sharePrice / invested);
        }

    }

    record SharePrice(double price) {

    }

    record Percent(double percentage) {

        public double of(double value) {
            return percentage * value;
        }

        public static Percent ofPercentage(double percentage) {
            return new Percent(percentage / 100);
        }
    }

    record Period(int years) {

        public static final int MONTHS_PER_YEAR = 12;

        public int monthsPerYear() {
            return MONTHS_PER_YEAR;
        }

        public int duration() {
            return monthsPerYear() * years;
        }

        public int yearOfMonth(int monthIndex) {
            return monthIndex / 12;
        }

        public <T> Series<T> series(T initialValue, BiFunction<Integer, T, T> generator) {
            List<T> data = new ArrayList<>(duration());
            data.add(initialValue);
            for (int i = 1; i <= duration(); i++) {
                data.add(generator.apply(i, data.get(i - 1)));
            }
            return new Series<>(data);
        }
    }

    record Series<T>(List<T> data) {

        public T get(int index) {
            return data.get(index);
        }

        public void set(int index, T value) {
            data.set(index, value);
        }
    }
}
