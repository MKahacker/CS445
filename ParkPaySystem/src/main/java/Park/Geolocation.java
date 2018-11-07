package Park;



public final class Geolocation {
    final double lat;
    final double lng;

    public Geolocation(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat(){
        return this.lat;
    }

    public double getLng(){
        return this.lng;
    }
}
