///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS org.jfree:jfreechart:1.5.2

import java.awt.BasicStroke;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.IntFunction;
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
        for (double depositFees : new double[]{4.5d}) {
            for (double adminFees : new double[]{0.28d}) {
                for (double averageYearlyPerformance : new double[]{5d}) {
                    for (long seed = 1L; seed < 20L; seed++) {
//                    for (long seed : new long[]{1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L}) {
                        Conditions conditions = new Conditions(period, initialSharePrice,
                                250d, Percent.ofPercentage(2d),
                                Percent.ofPercentage(depositFees), Percent.ofPercentage(adminFees),
                                Percent.ofPercentage(averageYearlyPerformance), Percent.ofPercentage(5d),
                                seed);
                        Fund fund = new Fund(conditions);
                        funds.add(fund);
                        for (int i = 1; i <= conditions.period.duration(); i++) {
                            fund.invest(i);
                        }
                    }
                }
            }
        }
        monthToMonthRelativePerformanceGraph(funds);
        cumlulatedRelativePerformanceGraph(funds);
        cumulatedAbsolutePerformanceGraph(funds);
        feesGraph(funds);
//        averageFund(period, funds);
    }

    private void averageFund(Period period, List<Fund> funds) {
        List<List<MonthlyReport>> reports = funds.stream().map(fund -> fund.reports).toList();
        List<MonthlyReport> averageReports = new ArrayList<>();
        for (int i = 0; i < period.duration(); i++) {
            int month = i + 1;
            double invested = 0d;
            double shares = 0d;
            double sharePrice = 0d;
            double performance = 0d;
            double depositFee = 0d;
            double adminFees = 0d;
            for (List<MonthlyReport> report : reports) {
                MonthlyReport monthlyReport = report.get(i);
                invested += monthlyReport.invested();
                shares += monthlyReport.shares();
                sharePrice += monthlyReport.sharePrice().price();
                performance += monthlyReport.performance().percentage();
                depositFee += monthlyReport.depositFee();
                adminFees += monthlyReport.adminFees();
            }
            invested /= reports.size();
            shares /= reports.size();
            sharePrice /= reports.size();
            performance /= reports.size();
            depositFee /= reports.size();
            adminFees /= reports.size();
            averageReports.add(new MonthlyReport(month, invested, shares, new SharePrice(sharePrice),
                    new Percent(performance), depositFee, adminFees));
        }
        Fund fund = new Fund(new Conditions(period, new SharePrice(100d),
                250d, Percent.ofPercentage(2d),
                Percent.ofPercentage(0d), Percent.ofPercentage(0d),
                Percent.ofPercentage(0d), Percent.ofPercentage(5d),
                1L));
        fund.reports.addAll(averageReports);
        List<Fund> averageFunds = List.of(fund);
        monthToMonthRelativePerformanceGraph(averageFunds);
        cumlulatedRelativePerformanceGraph(averageFunds);
        cumulatedAbsolutePerformanceGraph(averageFunds);
        feesGraph(averageFunds);
    }

    private static void monthToMonthRelativePerformanceGraph(List<Fund> funds) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (Fund fund : funds) {
            XYSeries sharePerformanceSeries = new XYSeries("Share Performance " + fund);
            XYSeries averagedPerformanceSeries = new XYSeries("Averaged Performance " + fund);
            double averagePerformance = 0d;
            int month = 1;
            for (MonthlyReport report : fund.reports) {
                double performance = report.performance().percentage() * 100;
                averagePerformance += performance;
                sharePerformanceSeries.add(report.month(), performance);
                averagedPerformanceSeries.add(
                        report.month(), averagePerformance / month);
                month++;
            }
            dataset.addSeries(sharePerformanceSeries);
            dataset.addSeries(averagedPerformanceSeries);
        }
        chart(dataset, "Month to month performance", "%", "month-to-month-performance");
    }

    private static void cumlulatedRelativePerformanceGraph(List<Fund> funds) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (Fund fund : funds) {
            XYSeries gainsSeries = new XYSeries("Gains " + fund);
            XYSeries sharePriceSeries = new XYSeries("Share Price " + fund);
            for (MonthlyReport report : fund.reports) {
                gainsSeries.add(report.month(), report.gains().percentage() * 100d);
                sharePriceSeries.add(
                        report.month(), report.sharePrice().price() / fund.conditions.initialSharePrice.price() * 100);
            }
            dataset.addSeries(gainsSeries);
            dataset.addSeries(sharePriceSeries);
        }
        chart(dataset, "Relative Performance", "%", "relative-performance");
    }

    private static void cumulatedAbsolutePerformanceGraph(List<Fund> funds) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (Fund fund : funds) {
            XYSeries invested = new XYSeries("Invested " + fund);
            XYSeries valueSeries = new XYSeries("Value " + fund);
            for (MonthlyReport report : fund.reports) {
                invested.add(report.month(), report.invested());
                valueSeries.add(report.month(), report.shares * report.sharePrice().price());
            }
            dataset.addSeries(invested);
            dataset.addSeries(valueSeries);
        }
        chart(dataset, "Absolute Performance", "€", "absolute-performance");
    }

    private void feesGraph(List<Fund> funds) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (Fund fund : funds) {
            XYSeries depositFeesSeries = new XYSeries("Deposit Fees " + fund);
            XYSeries adminFeesSeries = new XYSeries("Admin Fees " + fund);
            XYSeries allFeesSeries = new XYSeries("All Fees " + fund);
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
        private final SharePrice initialSharePrice;
        private final Percent investmentInflationCorrection;
        private final Percent depositFees;
        private final Percent adminFees;
        private final Percent averageYearlyPerformance;
        private final Percent averageMonthlyPerformance;
        private final Percent standardDeviation;
        private final long seed;
        private final Random random;
        private final Series<Percent> performance;

        public Conditions(Period period, SharePrice initialSharePrice,
                double initialInvestment, Percent investmentInflationCorrection,
                Percent depositFees, Percent adminFees,
                Percent averageYearlyPerformance, Percent standardDeviation, long seed) {
            this.period = period;
            this.initialSharePrice = initialSharePrice;
            this.investmentInflationCorrection = investmentInflationCorrection;
            this.depositFees = depositFees;
            this.adminFees = adminFees;
            this.averageYearlyPerformance = averageYearlyPerformance;
            this.standardDeviation = standardDeviation;
            this.seed = seed;
            this.random = new Random(seed);
            investments = period.series(monthIndex -> initialInvestment * Math.pow(
                    1 + this.investmentInflationCorrection.percentage(),
                    period.yearOfMonth(monthIndex)));
            averageMonthlyPerformance = new Percent(Math.pow(1 + averageYearlyPerformance.percentage(), 1d / 12d) - 1);
            performance = period.series(monthIndex -> {
                double mean = averageMonthlyPerformance.percentage();
                return new Percent(mean + this.standardDeviation.percentage() * random.nextGaussian());
            });

        }
    }

    public static class Fund {

        private final Conditions conditions;
        private int month = 0;
        private double shares = 0d;
        private SharePrice sharePrice;

        private double invested = 0d;

        private final List<MonthlyReport> reports = new ArrayList<>();

        public Fund(Conditions conditions) {
            this.conditions = conditions;
            sharePrice = conditions.initialSharePrice;
        }

        public void invest(int periodIndex) {
            month = periodIndex;
            Percent performance = conditions.performance.get(periodIndex);
            double sharePriceIncrease = performance.of(sharePrice.price);
            sharePrice = new SharePrice(sharePrice.price + sharePriceIncrease);
            double investment = conditions.investments.get(periodIndex);
            invested += investment;
            double depositFee = conditions.depositFees.of(investment);
            double valueInvested = investment - depositFee;
            double sharedBought = valueInvested / sharePrice.price();
            shares += sharedBought;
            double adminFeesInShares;
            if (month % 12 == 0 && month > 0) {
                adminFeesInShares = conditions.adminFees.of(shares);
            } else {
                adminFeesInShares = 0d;
            }
            shares -= adminFeesInShares;
            reports.add(report(performance, depositFee, adminFeesInShares * sharePrice.price()));
        }

        public MonthlyReport report(Percent performance, double depositFee,
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

    public record MonthlyReport(int month, double invested, double shares, SharePrice sharePrice,
                                Percent performance, double depositFee, double adminFees) {

        public Percent gains() {
            return new Percent(shares * sharePrice.price() / invested);
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

        public <T> Series<T> series(IntFunction<T> generator) {
            List<T> data = new ArrayList<>(duration());
            for (int i = 0; i < duration(); i++) {
                data.add(generator.apply(i));
            }
            return new Series<>(data);
        }
    }

    record Series<T>(List<T> data) {

        public T get(int index) {
            return data.get(index - 1);
        }

        public void set(int index, T value) {
            data.set(index - 1, value);
        }
    }
}
