package tixeaseadmin;

public class Transaction {
    private int id;
    private int idPengunjung;
    private int eventId;
    private int jumlahTiket;
    private double totalAmount;
    private double payment;
    private double changeAmount;
    private String namaPengunjung;
    private String noTlp;

    public Transaction(int id, int idPengunjung, int eventId, int jumlahTiket, double totalAmount,
                       double payment, double changeAmount, String namaPengunjung, String noTlp) {
        this.id = id;
        this.idPengunjung = idPengunjung;
        this.eventId = eventId;
        this.jumlahTiket = jumlahTiket;
        this.totalAmount = totalAmount;
        this.payment = payment;
        this.changeAmount = changeAmount;
        this.namaPengunjung = namaPengunjung;
        this.noTlp = noTlp;
    }

    public int getId() {
        return id;
    }

    public int getIdPengunjung() {
        return idPengunjung;
    }

    public int getEventId() {
        return eventId;
    }

    public int getJumlahTiket() {
        return jumlahTiket;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getPayment() {
        return payment;
    }

    public double getChangeAmount() {
        return changeAmount;
    }

    public String getNamaPengunjung() {
        return namaPengunjung;
    }

    public String getNoTlp() {
        return noTlp;
    }
}
