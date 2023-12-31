package mihaijulien.eu.msscbrewery.web.controller.v2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import mihaijulien.eu.msscbrewery.services.v2.BeerServiceV2;
import mihaijulien.eu.msscbrewery.web.model.v2.BeerDTOv2;
import mihaijulien.eu.msscbrewery.web.model.v2.BeerPagedList;
import mihaijulien.eu.msscbrewery.web.model.v2.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Api(value = "Beer V2")
@RequestMapping("/api/v2/beer")
@RestController
public class BeerControllerV2 {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerServiceV2 beerService;

    public BeerControllerV2(BeerServiceV2 beerService) {
        this.beerService = beerService;
    }

    @GetMapping(produces = { "application/json" }, path = "beer")
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle){

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize));

        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    @GetMapping("/{beerId}")
    @ApiOperation(value="Returns a beer given its id")
    public ResponseEntity<BeerDTOv2> getBeer(@NotNull @PathVariable("beerId") UUID beerId){
        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value="Save a new beer")
    public ResponseEntity<BeerDTOv2> saveNewBeer(@Valid @NotNull @RequestBody BeerDTOv2 beerDTOv2){
        BeerDTOv2 savedBeerDTO = beerService.saveNewBeer(beerDTOv2);

        return new ResponseEntity<>(savedBeerDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    @ApiOperation(value="Update beer information")
    public ResponseEntity<BeerDTOv2> updateBeer(@PathVariable UUID beerID, @Valid @RequestBody BeerDTOv2 beerDTOv2){
        BeerDTOv2 updatedBeerDTOv2 = beerService.updateBeer(beerID, beerDTOv2);

        return new ResponseEntity(updatedBeerDTOv2, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    @ApiOperation(value="Delete a specific beer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<BeerDTOv2> deleteBeer(@PathVariable UUID beerId){
        beerService.deleteById(beerId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
