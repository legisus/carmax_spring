package scanner.dispetchers;

import core.model.Auction;
import core.model.enums.Locations;
import lombok.extern.slf4j.Slf4j;
import scanner.defenitionSteps.CarMaxScraper;
import scanner.defenitionSteps.JdpScraper;
import scanner.defenitionSteps.MainheimScraper;
import scanner.utils.InitCarListFile;
import utils_api.CarUtils;
import utils_api.ConstantUtils;

import java.time.LocalDate;
import java.util.HashSet;

@Slf4j
public class GetAuctionData {
    private CarMaxScraper cmx;
    private JdpScraper jdp;
    private MainheimScraper mmr;
    private GetAuctionLib lib;

    public GetAuctionData(CarMaxScraper cmx, JdpScraper jdp, MainheimScraper mmr, GetAuctionLib getAuctionLib) {
        this.cmx = cmx;
        this.jdp = jdp;
        this.mmr = mmr;
        this.lib = getAuctionLib;
    }

    public static void main(String[] args) {

        GetAuctionData dis =
                new GetAuctionData(new CarMaxScraper(), new JdpScraper(), new MainheimScraper(), new GetAuctionLib());

        dis.lib.setAuction(new Auction());
        dis.lib.getAuction().setLocation(Locations.IRVINE);
        dis.lib.getAuction().setDateOfAuction(LocalDate.of(2024, 5, 15));

        dis.lib.setPath("C:/props/objects/" + dis.lib.getAuction().getDateOfAuction() +
                "_" + dis.lib.getAuction().getLocation().getLocation() + "_carList.json");

        log.info(ConstantUtils.DESERIALIZE_MESSAGE);
        dis.lib.setCarList(
                InitCarListFile.initCarList(
                        dis.lib.getCarList(),
                        dis.lib.getPath()));
        log.info(ConstantUtils.SUCCESS_MESSAGE);

        dis.cmx.openCarMaxActionPage();
        dis.cmx.signInCarMax();
        dis.cmx.goToCarMaxAuctions();
        dis.cmx.goToAvailableAuction(dis.lib.getAuction().getLocation().getLocation());
        dis.cmx.closeDialogIfAppeared();

        dis.lib.setNewCarList(dis.cmx.saveAllCarsFromElementsWithScroll());

        if (dis.lib.getCarList() == null) dis.lib.setCarList(dis.lib.getNewCarList());

        if(dis.lib.getCarList().size() != dis.lib.getNewCarList().size()) {
            log.info("New cars were added to the list");
            dis.lib.setUniqueCars(CarUtils.getUniqueCars(dis.lib.getCarList(),
                    dis.lib.getNewCarList()));
            log.info("Unique cars: " + dis.lib.getUniqueCars().size());
            dis.lib.getUniqueCars().forEach(car -> log.info(car.toString()));
            dis.lib.getCarList().addAll(dis.lib.getUniqueCars());
        }

        dis.lib.getAuction().setCars(new HashSet<>(dis.lib.getCarList()));
        dis.lib.getCarList().forEach(car -> car.setAuction(dis.lib.getAuction()));

        log.info(ConstantUtils.SERIALIZE_MESSAGE);
        CarUtils.saveAuctionToJsonFile(dis.lib.getAuction(), dis.lib.getPath());
        log.info(ConstantUtils.SUCCESS_MESSAGE);

        dis.cmx.signOutCarMax();
        dis.cmx.close();
    }
}
