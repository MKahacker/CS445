package App;
/*
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/parkpay")
public class ParkController {

    @GetMapping("/parks")
    public JsonNode getAllParks(){
        JsonNode parks;
        try {
            parks = parksMapper.readTree(myParks.getAllParksInfo().toString());
            return parks;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/parks/{id}")
    public JsonNode getAPark(@PathVariable("id") int pid){
        JsonNode apark;
        try {
            apark = parksMapper.readTree(myParks.getSpecificParkInfo(pid).toString());
            return apark;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
*/