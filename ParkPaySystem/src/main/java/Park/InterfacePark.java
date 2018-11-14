package Park;

import org.json.JSONObject;

public interface InterfacePark {
    JSONObject viewInformation();
    int getParkId();

    double inStateFee(int paymentType);

    double outStateFee(int paymentType);
}
