package stripepayments;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StripePaymentsConverter {

    public static void main(String[] args) throws IOException {
        try (InputStream is = StripePaymentsConverter.class.getResourceAsStream("2020-01-01-to-2020-05-10.csv");
             BufferedReader buffer = new BufferedReader(new InputStreamReader(is))) {
            CSVReader csvReader = new CSVReaderBuilder(buffer)
                    .withCSVParser(new CSVParserBuilder()
                            .withSeparator(',')
                            .build())
                    .build();
            String[] headers = csvReader.readNext();
            Map<String, Integer> columnIndexes = columnIndexes(headers);
            StreamSupport.stream(csvReader.spliterator(), false)
                    .map(line -> parse(line, columnIndexes))
                    .filter(Payment::isUsBackendPayment)
                    .collect(Collectors.groupingBy(Payment::getDate, TreeMap::new, Collectors.toList()))
                    .entrySet()
                    .stream()
                    .map(e -> new PaymentStats(e.getKey(), e.getKey(), e.getValue()))
                    .sorted(Comparator.comparing(PaymentStats::getFrom))
                    .forEach(System.out::println);
        }
    }

    private static Map<String, Integer> columnIndexes(String[] headers) {
        Map<String, Integer> columnIndexes = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            columnIndexes.put(headers[i], i);
        }
        return columnIndexes;
    }

    private static Payment parse(String[] line, Map<String, Integer> columnIndexes) {
        return new Payment(line, columnIndexes);
    }

    private static class Payment {

        private final String[] columns;
        private final Map<String, Integer> columnIndexes;

        private Payment(String[] columns, Map<String, Integer> columnIndexes) {
            this.columns = columns;
            this.columnIndexes = columnIndexes;
        }

        private boolean isUsBackendPayment() {
            return "US".equals(get("mySugr-Backend-Region (metadata)"));
        }

        private String get(String column) {
            return columns[columnIndexes.get(column)];
        }

        @Override
        public String toString() {
            return Arrays.toString(columns);
        }

        public LocalDate getDate() {
            return LocalDate.parse(get("Created (UTC)").substring(0, 10));
        }

        public boolean isFailed() {
            return "Failed".equals(get("Status"));
        }

        public String getSellerMessage() {
            return get("Seller Message");
        }
    }

    private static class PaymentStats {

        private final LocalDate from;
        private final LocalDate to;
        private final long nbPayments;
        private long nbPaymentFailures;
        private long nbFailuresTooRisky;
        private long nbFailuresDoNotHonor;
        private long nbFailuresStolenCard;
        private long nbFailuresInsufficientFunds;
        private long nbFailuresTrxNotAllowed;
        private long nbFailuresIncorrectNumber;
        private long nbFailuresPickupCard;
        private long nbFailuresLostCard;

        public PaymentStats(LocalDate from, LocalDate to, List<Payment> payments) {
            this.from = from;
            this.to = to;
            nbPayments = payments.size();
            for (Payment failure : payments.stream().filter(Payment::isFailed).collect(Collectors.toList())) {
                nbPaymentFailures++;
                String sellerMessage = failure.getSellerMessage();
                switch (sellerMessage) {
                    case "Stripe blocked this payment as too risky.":
                        nbFailuresTooRisky++;
                        break;
                    case "The bank returned the decline code `do_not_honor`.":
                        nbFailuresDoNotHonor++;
                        break;
                    case "The bank returned the decline code `stolen_card`.":
                        nbFailuresStolenCard++;
                        break;
                    case "The bank returned the decline code `insufficient_funds`.":
                        nbFailuresInsufficientFunds++;
                        break;
                    case "The bank returned the decline code `transaction_not_allowed`.":
                        nbFailuresTrxNotAllowed++;
                        break;
                    case "The bank returned the decline code `incorrect_number`.":
                        nbFailuresIncorrectNumber++;
                        break;
                    case "The bank returned the decline code `pickup_card`.":
                        nbFailuresPickupCard++;
                        break;
                    case "The bank returned the decline code `lost_card`.":
                        nbFailuresLostCard++;
                        break;
                    //                    Stripe blocked this payment as too risky.
                    //                    The bank returned the decline code `do_not_honor`.
                    //                    The bank returned the decline code `stolen_card`.
                    //                    The bank returned the decline code `insufficient_funds`.
                    //                    The bank returned the decline code `transaction_not_allowed`.
                    //                    The bank returned the decline code `incorrect_number`.
                    //                    The bank returned the decline code `pickup_card`.
                    //                    The bank returned the decline code `lost_card`.
                    default:
                        throw new IllegalStateException("Unknown failure: " + sellerMessage);
                }
            }
        }

        public LocalDate getFrom() {
            return from;
        }

        @Override
        public String toString() {
            return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                    from, to, nbPayments, nbPaymentFailures / (double) nbPayments,
                    nbFailuresTooRisky / (double) nbPayments, nbFailuresDoNotHonor / (double) nbPayments,
                    nbFailuresStolenCard / (double) nbPayments, nbFailuresInsufficientFunds / (double) nbPayments,
                    nbFailuresTrxNotAllowed / (double) nbPayments, nbFailuresIncorrectNumber / (double) nbPayments,
                    nbFailuresPickupCard / (double) nbPayments, nbFailuresLostCard / (double) nbPayments);
        }
    }
}
