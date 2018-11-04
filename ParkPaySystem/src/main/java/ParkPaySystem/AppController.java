package ParkPaySystem;

import org.json.JSONArray;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AppController {
    private List<Park> listPark = new ArrayList<Park>();
    private ParkInteractor myParks = new ParkInteractor(listPark);

    @GetMapping("/parks")
    public JSONArray getAllParks(){
        return this.myParks.getAllParksInfo();
    }


}
