package ParkPaySystem;

public class Order {
    int id, parkId, vistorId;
    int amount;
    int zip;
    String state, plate, vechileType;
    String name, email;
    String card, nameOnCard, expirationDate;


    public Order(int id, int parkId, int amount, int zip, String state, String plate, String vechileType, String email
        , String card, String nameOnCard, String expirationDate){
        this.id = id;
        this.parkId = parkId;
        this.vistorId = -1;
        this.amount = amount;
        this.zip = zip;
        this.state = state;
        this.plate = plate;
        this.vechileType = vechileType;
        this.name = "";
        this.email = email;
        this.card = card;
        this.nameOnCard = nameOnCard;
        this.expirationDate = expirationDate;
    }

    public Order(int id, int parkId, int vistorId, int amount, int zip, String state, String plate, String vechileType,
                 String email, String name, String card, String nameOnCard, String expirationDate){
        this.id = id;
        this.parkId = parkId;
        this.vistorId = vistorId;
        this.amount = amount;
        this.zip = zip;
        this.state = state;
        this.plate = plate;
        this.vechileType = vechileType;
        this.name = name;
        this.email = email;
        this.card = card;
        this.nameOnCard = nameOnCard;
        this.expirationDate = expirationDate;
    }

    public void setVistorId(int vistorId){
        this.vistorId = vistorId;
    }

    public void setName(String name){
        this.name = name;
    }

}
