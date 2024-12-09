package org.smirnovav.moexFuturesData.service;

import org.smirnovav.moexFuturesData.db.entity.*;
import org.smirnovav.moexFuturesData.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class DbService {

    private AssetCodeRepository assetCodeRepository;
    private BoardIdRepository boardIdRepository;
    private DecimalsRepository decimalsRepository;
    private FuturesDayHistoryRepository futuresDayHistoryRepository;
    private FuturesRepository futuresRepository;
    private FuturesMarketDataRepository futuresMarketDataRepository;
    private MinStepRepository minStepRepository;
    private SecTypeRepository secTypeRepository;

    @Autowired
    public DbService(AssetCodeRepository assetCodeRepository, BoardIdRepository repository,
                     DecimalsRepository decimalsRepository, FuturesDayHistoryRepository futuresDayHistoryRepository,
                     FuturesRepository futuresRepository, FuturesMarketDataRepository futuresMarketDataRepository,
                     MinStepRepository minStepRepository, SecTypeRepository secTypeRepository) {
        this.assetCodeRepository = assetCodeRepository;
        this.boardIdRepository = repository;
        this.decimalsRepository = decimalsRepository;
        this.futuresDayHistoryRepository = futuresDayHistoryRepository;
        this.futuresRepository = futuresRepository;
        this.futuresMarketDataRepository = futuresMarketDataRepository;
        this.minStepRepository = minStepRepository;
        this.secTypeRepository = secTypeRepository;
    }

    public void saveData(IntegratedFuturesInfoEntity integratedFuturesInfoEntity) {

        FuturesEntity futuresEntity = integratedFuturesInfoEntity.getFuturesEntity();
        FuturesEntity update = futuresRepository.save(futuresEntity);
        integratedFuturesInfoEntity.getFuturesMarketDataEntity().setFuturesEntity(update);
        for (FuturesDayHistoryEntity futuresDayHistoryEntity : integratedFuturesInfoEntity.getFuturesDayHistoryEntityList()) {
            futuresDayHistoryEntity.setFuturesEntity(update);
        }
        AssetCodeEntity assetCodeEntity;
        BoardIdEntity boardIdEntity;
        DecimalsEntity decimalsEntity;
        MinStepEntity minStepEntity;
        SecTypeEntity secTypeEntity;


        boolean isAssetCodePresent = assetCodeRepository.existsById(integratedFuturesInfoEntity.getAssetCodeEntity().getAssetCode());
        boolean isBoardIdPresent = boardIdRepository.existsById(integratedFuturesInfoEntity.getBoardIdEntity().getBoardName());
        boolean isDecimalsPresent = decimalsRepository.existsById(integratedFuturesInfoEntity.getDecimalsEntity().getDecimals());
        boolean isMinStepPresent = minStepRepository.existsById(integratedFuturesInfoEntity.getMinStepEntity().getMinStep());
        boolean isSecTypePresent = secTypeRepository.existsById(integratedFuturesInfoEntity.getSecTypeEntity().getSecType());
        if (isAssetCodePresent) {
            assetCodeEntity = assetCodeRepository.getReferenceById(integratedFuturesInfoEntity.getAssetCodeEntity().getAssetCode());
            update.setAssetCodeEntity(assetCodeEntity);
            if (assetCodeEntity.getFuturesEntities() == null) {
                Set<FuturesEntity> futuresEntities = Set.of(update);
                assetCodeEntity.setFuturesEntities(futuresEntities);
            } else {
                assetCodeEntity.getFuturesEntities().add(update);
            }
        } else {
            assetCodeEntity = integratedFuturesInfoEntity.getAssetCodeEntity();
            Set<FuturesEntity> futuresEntities = Set.of(update);
            assetCodeEntity.setFuturesEntities(futuresEntities);
        }

        if (isBoardIdPresent) {
            boardIdEntity = boardIdRepository.getReferenceById(integratedFuturesInfoEntity.getBoardIdEntity().getBoardName());
            update.setBoardIdEntity(boardIdEntity);
            if (boardIdEntity.getFuturesEntities() == null) {
                Set<FuturesEntity> futuresEntities = Set.of(update);
                boardIdEntity.setFuturesEntities(futuresEntities);
            } else {
                boardIdEntity.getFuturesEntities().add(update);
            }
        } else {
            boardIdEntity = integratedFuturesInfoEntity.getBoardIdEntity();
            Set<FuturesEntity> futuresEntities = Set.of(update);
            boardIdEntity.setFuturesEntities(futuresEntities);
        }

        if (isDecimalsPresent) {
            decimalsEntity = decimalsRepository.getReferenceById(integratedFuturesInfoEntity.getDecimalsEntity().getDecimals());
            update.setDecimalsEntity(decimalsEntity);
            if (decimalsEntity.getFuturesEntities() == null) {
                Set<FuturesEntity> futuresEntities = Set.of(update);
                decimalsEntity.setFuturesEntities(futuresEntities);
            } else {
                decimalsEntity.getFuturesEntities().add(update);
            }
        } else {
            decimalsEntity = integratedFuturesInfoEntity.getDecimalsEntity();
            Set<FuturesEntity> futuresEntities = Set.of(update);
            decimalsEntity.setFuturesEntities(futuresEntities);
        }

        if (isMinStepPresent) {
            minStepEntity = minStepRepository.getReferenceById(integratedFuturesInfoEntity.getMinStepEntity().getMinStep());
            update.setMinStepEntity(minStepEntity);
            if (minStepEntity.getFuturesEntities() == null) {
                Set<FuturesEntity> futuresEntities = Set.of(update);
                minStepEntity.setFuturesEntities(futuresEntities);
            } else {
                minStepEntity.getFuturesEntities().add(update);
            }
        } else {
            minStepEntity = integratedFuturesInfoEntity.getMinStepEntity();
            Set<FuturesEntity> futuresEntities = Set.of(update);
            minStepEntity.setFuturesEntities(futuresEntities);
        }

        if (isSecTypePresent) {
            secTypeEntity = secTypeRepository.getReferenceById(integratedFuturesInfoEntity.getSecTypeEntity().getSecType());
            update.setSecTypeEntity(secTypeEntity);
            if (secTypeEntity.getFuturesEntities() == null) {
                Set<FuturesEntity> futuresEntities = Set.of(update);
                secTypeEntity.setFuturesEntities(futuresEntities);
            } else {
                secTypeEntity.getFuturesEntities().add(update);
            }
        } else {
            secTypeEntity = integratedFuturesInfoEntity.getSecTypeEntity();
            Set<FuturesEntity> futuresEntities = Set.of(update);
            secTypeEntity.setFuturesEntities(futuresEntities);
        }
        futuresMarketDataRepository.save(integratedFuturesInfoEntity.getFuturesMarketDataEntity());





//        cleanFuturesRepositories();

//        System.out.println(isAssetCodePresent + " " + isBoardIdPresent + " " + isDecimalsPresent + " " + isMinStepPresent + " " +
//                isSecTypePresent);

        System.out.println(integratedFuturesInfoEntity.getFuturesMarketDataEntity().getOpen());
//        FuturesEntity update = futuresRepository.save(futuresEntity);
        System.out.println(update.getDecimalsEntity().getDecimals());
//        futuresMarketDataRepository.save(integratedFuturesInfoEntity.getFuturesMarketDataEntity());
//        futuresDayHistoryRepository.saveAll(integratedFuturesInfoEntity.getFuturesDayHistoryEntityList());

//        assetCodeRepository.save(assetCodeEntity);
//        boardIdRepository.save(boardIdEntity);
//        decimalsRepository.save(decimalsEntity);
//        minStepRepository.save(minStepEntity);
//        secTypeRepository.save(secTypeEntity);


//        flushAll();


//        boolean isFuturesPresent = futuresRepository.existsById(integratedFuturesInfoEntity.getFuturesEntity().getShortName());

    }

    public void saveDataAlt(IntegratedFuturesInfoEntity integratedFuturesInfoEntity) {

//        AssetCodeEntity assetCodeEntity = integratedFuturesInfoEntity.getAssetCodeEntity();
        AssetCodeEntity assetCodeEntity = assetCodeRepository.findById(integratedFuturesInfoEntity.getAssetCodeEntity().getAssetCode())
                .orElse(integratedFuturesInfoEntity.getAssetCodeEntity());
        BoardIdEntity boardIdEntity = boardIdRepository.findById(integratedFuturesInfoEntity.getBoardIdEntity().getBoardName())
                .orElse(integratedFuturesInfoEntity.getBoardIdEntity());
        DecimalsEntity decimalsEntity = decimalsRepository.findById(integratedFuturesInfoEntity.getDecimalsEntity().getDecimals())
                .orElse(integratedFuturesInfoEntity.getDecimalsEntity());
        List<FuturesDayHistoryEntity> futuresDayHistoryEntityList = integratedFuturesInfoEntity.getFuturesDayHistoryEntityList();
        FuturesEntity futuresEntity = integratedFuturesInfoEntity.getFuturesEntity();
        FuturesMarketDataEntity futuresMarketDataEntity = integratedFuturesInfoEntity.getFuturesMarketDataEntity();
        MinStepEntity minStepEntity = minStepRepository.findById(integratedFuturesInfoEntity.getMinStepEntity().getMinStep())
                .orElse(integratedFuturesInfoEntity.getMinStepEntity());
        SecTypeEntity secTypeEntity = secTypeRepository.findById(integratedFuturesInfoEntity.getSecTypeEntity().getSecType())
                .orElse(integratedFuturesInfoEntity.getSecTypeEntity());


        futuresEntity.setFuturesDayHistoryEntityList(futuresDayHistoryEntityList);

        futuresMarketDataEntity.setFuturesEntity(futuresEntity);

        futuresEntity.setFuturesMarketDataEntity(integratedFuturesInfoEntity.getFuturesMarketDataEntity());
        for (FuturesDayHistoryEntity futuresDayHistoryEntity : futuresDayHistoryEntityList) {
            futuresDayHistoryEntity.setFuturesEntity(futuresEntity);
        }


        assetCodeEntity.addFuturesEntity(futuresEntity);
        boardIdEntity.addFuturesEntity(futuresEntity);
        decimalsEntity.addFuturesEntity(futuresEntity);
        minStepEntity.addFuturesEntity(futuresEntity);
        secTypeEntity.addFuturesEntity(futuresEntity);

        futuresEntity.setAssetCodeEntity(assetCodeEntity);
        futuresEntity.setBoardIdEntity(boardIdEntity);
        futuresEntity.setDecimalsEntity(decimalsEntity);
        futuresEntity.setMinStepEntity(minStepEntity);
        futuresEntity.setSecTypeEntity(secTypeEntity);


        futuresDayHistoryRepository.saveAll(integratedFuturesInfoEntity.getFuturesDayHistoryEntityList());
        futuresMarketDataRepository.save(futuresMarketDataEntity);
        futuresRepository.deleteById(integratedFuturesInfoEntity.getFuturesEntity().getShortName());


//
//
//
//        FuturesEntity updatedFuturesEntity = futuresRepository.save(futuresEntity);


//        AssetCodeEntity updatedAssetCodeEntity = assetCodeRepository.saveAndFlush(assetCodeEntity);
//        BoardIdEntity updatedBoardIdEntity = boardIdRepository.saveAndFlush(boardIdEntity);
//        DecimalsEntity updatedDecimalsEntity = decimalsRepository.saveAndFlush(decimalsEntity);
//        MinStepEntity updatedMinStepEntity = minStepRepository.saveAndFlush(minStepEntity);
//        SecTypeEntity updatedSecTypeEntity = secTypeRepository.saveAndFlush(secTypeEntity);



//        futuresEntity.setAssetCodeEntity(updatedAssetCodeEntity);
//        futuresEntity.setBoardIdEntity(updatedBoardIdEntity);
//        futuresEntity.setDecimalsEntity(updatedDecimalsEntity);
//        futuresEntity.setMinStepEntity(updatedMinStepEntity);
//        futuresEntity.setSecTypeEntity(updatedSecTypeEntity);




//        assetCodeRepository.save(updatedAssetCodeEntity);




//        updatedFuturesEntity.setFuturesMarketDataEntity(integratedFuturesInfoEntity.getFuturesMarketDataEntity());
//        updatedFuturesEntity.setFuturesDayHistoryEntityList(futuresDayHistoryEntityList);
//        for (FuturesDayHistoryEntity futuresDayHistoryEntity : futuresDayHistoryEntityList) {
//            futuresDayHistoryEntity.setFuturesEntity(updatedFuturesEntity);
//        }
//        futuresMarketDataEntity.setFuturesEntity(updatedFuturesEntity);


        flushAll();


    }

    public void flushAll() {
        this.assetCodeRepository.flush();
        this.boardIdRepository.flush();
        this.decimalsRepository.flush();
        this.futuresDayHistoryRepository.flush();
        this.futuresRepository.flush();
        this.futuresMarketDataRepository.flush();
        this.minStepRepository.flush();
        this.secTypeRepository.flush();
    }

    public void cleanFuturesRepositories() {
        futuresRepository.deleteAll();
        futuresMarketDataRepository.deleteAll();
        futuresDayHistoryRepository.deleteAll();
    }

    public void cleanDb() {
        assetCodeRepository.deleteAll();
        boardIdRepository.deleteAll();
        decimalsRepository.deleteAll();
        futuresRepository.deleteAllInBatch();
        futuresMarketDataRepository.deleteAll();
        futuresDayHistoryRepository.deleteAll();
        minStepRepository.deleteAll();
        secTypeRepository.deleteAll();
    }

    public String getById() {
        StringBuilder builder = new StringBuilder();
        AssetCodeEntity assetCodeEntity = assetCodeRepository.findById("GAZR").orElse(new AssetCodeEntity());
        System.out.println(assetCodeEntity.getAssetCode());
        Set<FuturesEntity> futuresEntities = assetCodeEntity.getFuturesEntities();
        for (FuturesEntity futuresEntity : futuresEntities) {
            builder.append(futuresEntity.getSecId()).append(" ");
        }
        return builder.toString();
    }


}
