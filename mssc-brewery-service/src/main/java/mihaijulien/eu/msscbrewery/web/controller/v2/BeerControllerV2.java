package mihaijulien.eu.msscbrewery.web.controller.v2;

import mihaijulien.eu.msscbrewery.services.v2.BeerServiceV2;
import mihaijulien.eu.msscbrewery.web.model.v2.BeerDTOv2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v2/beer")
@RestController
public class BeerControllerV2 {
    private final BeerServiceV2 beerService;

    public BeerControllerV2(BeerServiceV2 beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDTOv2> getBeer(@PathVariable("beerId") UUID beerId){

        // TODO impl
        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDTOv2> saveNewBeer(@RequestBody BeerDTOv2 beerDTOv2){
        BeerDTOv2 savedBeerDTO = beerService.saveNewBeer(beerDTOv2);

        // TODO impl
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDTOv2> updateBeer(@PathVariable UUID beerID,  @RequestBody BeerDTOv2 beerDTOv2){
        beerService.updateBeer(beerID, beerDTOv2);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable UUID beerId){
        beerService.deleteById(beerId);
    }
}