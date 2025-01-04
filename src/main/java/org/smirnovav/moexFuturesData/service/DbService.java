package org.smirnovav.moexFuturesData.service;

import org.smirnovav.moexFuturesData.calculations.collectors.futures.FuturesFullSpecification;
import org.smirnovav.moexFuturesData.db.entity.*;
import org.smirnovav.moexFuturesData.db.repository.*;
import org.smirnovav.moexFuturesData.dto.IntegratedFuturesInfoDto;
import org.smirnovav.moexFuturesData.mapper.FuturesMapper;
import org.smirnovav.moexFuturesData.utils.Helpers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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


    public void saveFuturesData(IntegratedFuturesInfoEntity integratedFuturesInfoEntity) {

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

        futuresEntity.setAssetCodeEntity(assetCodeEntity);
        futuresEntity.setBoardIdEntity(boardIdEntity);
        futuresEntity.setDecimalsEntity(decimalsEntity);
        futuresEntity.setMinStepEntity(minStepEntity);
        futuresEntity.setSecTypeEntity(secTypeEntity);

        boolean isFuturesEntityExists = futuresRepository.existsById(futuresEntity.getShortName());
        if (isFuturesEntityExists) {
            FuturesEntity updatedFuturesEntity = futuresRepository.findById(futuresEntity.getShortName()).orElse(futuresEntity);
            updatedFuturesEntity.update(futuresEntity);
            FuturesMarketDataEntity updatedFuturesMarketDataEntity = updatedFuturesEntity.getFuturesMarketDataEntity();
            updatedFuturesMarketDataEntity.update(futuresMarketDataEntity);
            futuresMarketDataRepository.save(updatedFuturesMarketDataEntity);
            futuresRepository.save(updatedFuturesEntity);
            List<FuturesDayHistoryEntity> history = futuresDayHistoryRepository.findAllByFuturesEntity(updatedFuturesEntity);
            for (FuturesDayHistoryEntity futuresDayHistoryEntity : futuresDayHistoryEntityList) {
                futuresDayHistoryEntity.setFuturesEntity(updatedFuturesEntity);
            }
            futuresDayHistoryRepository.saveAll(integratedFuturesInfoEntity.getFuturesDayHistoryEntityList());
            futuresDayHistoryRepository.deleteAllInBatch(history);
        } else {
            futuresDayHistoryRepository.saveAll(integratedFuturesInfoEntity.getFuturesDayHistoryEntityList());
        }
        flushAll();
    }

    public void saveAllFuturesData(List<IntegratedFuturesInfoEntity> integratedFuturesInfoEntities) {
        int counter = 1;
        for (IntegratedFuturesInfoEntity integratedFuturesInfoEntity : integratedFuturesInfoEntities) {
            saveFuturesData(integratedFuturesInfoEntity);
            Helpers.printAngCarriageGoBack("Saving entity... SecId # " + counter + " = " + integratedFuturesInfoEntity.getFuturesEntity().getSecId());
            counter++;
        }
        System.out.print("\n");
    }

    public IntegratedFuturesInfoDto getIntegratedFuturesInfoDto(String shortName) {
        FuturesEntity futuresEntity = futuresRepository.findById(shortName).orElse(new FuturesEntity());
        List<FuturesDayHistoryEntity> history = futuresDayHistoryRepository.findAllByFuturesEntity(futuresEntity);
        IntegratedFuturesInfoEntity integratedFuturesInfoEntity = IntegratedFuturesInfoEntity.builder()
                .assetCodeEntity(futuresEntity.getAssetCodeEntity())
                .boardIdEntity(futuresEntity.getBoardIdEntity())
                .decimalsEntity(futuresEntity.getDecimalsEntity())
                .futuresDayHistoryEntityList(history)
                .futuresEntity(futuresEntity)
                .futuresMarketDataEntity(futuresEntity.getFuturesMarketDataEntity())
                .minStepEntity(futuresEntity.getMinStepEntity())
                .secTypeEntity(futuresEntity.getSecTypeEntity())
                .build();
        return FuturesMapper.integratedInfoEntityToDto(integratedFuturesInfoEntity);
    }

    public List<IntegratedFuturesInfoDto> getAllIntegratedFuturesInfoDto() {
        List<IntegratedFuturesInfoDto> integratedFuturesInfoDtos = new ArrayList<>();
        List<FuturesEntity> futuresEntities = futuresRepository.findAll();
        for (FuturesEntity futuresEntity : futuresEntities) {
            List<FuturesDayHistoryEntity> history = futuresDayHistoryRepository.findAllByFuturesEntity(futuresEntity);
            integratedFuturesInfoDtos.add(FuturesMapper.integratedInfoEntityToDto(IntegratedFuturesInfoEntity.builder()
                    .assetCodeEntity(futuresEntity.getAssetCodeEntity())
                    .boardIdEntity(futuresEntity.getBoardIdEntity())
                    .decimalsEntity(futuresEntity.getDecimalsEntity())
                    .futuresDayHistoryEntityList(history)
                    .futuresEntity(futuresEntity)
                    .futuresMarketDataEntity(futuresEntity.getFuturesMarketDataEntity())
                    .minStepEntity(futuresEntity.getMinStepEntity())
                    .secTypeEntity(futuresEntity.getSecTypeEntity())
                    .build()));
        }
        return integratedFuturesInfoDtos;
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
        futuresRepository.deleteAllInBatch();
        futuresMarketDataRepository.deleteAllInBatch();
        futuresDayHistoryRepository.deleteAllInBatch();
    }

    public void deleteTicker(FuturesEntity futuresEntity) {
        List<FuturesDayHistoryEntity> historyEntities = futuresDayHistoryRepository.findAllByFuturesEntity(futuresEntity);
        futuresDayHistoryRepository.deleteAll(historyEntities);
        futuresDayHistoryRepository.flush();
        futuresRepository.deleteById(futuresEntity.getShortName());
        futuresRepository.flush();
        futuresRepository.deleteById(futuresEntity.getShortName());
        futuresRepository.flush();
    }

    public void cleanDb() {
        assetCodeRepository.deleteAllInBatch();
        boardIdRepository.deleteAllInBatch();
        decimalsRepository.deleteAllInBatch();
        futuresRepository.deleteAllInBatch();
        futuresMarketDataRepository.deleteAllInBatch();
        futuresDayHistoryRepository.deleteAllInBatch();
        minStepRepository.deleteAllInBatch();
        secTypeRepository.deleteAllInBatch();
    }

    public FuturesFullSpecification getFullFutSpecByShortName(String shortName) {
        FuturesEntity futuresEntity = futuresRepository.findById(shortName).orElse(new FuturesEntity());
        IntegratedFuturesInfoEntity integratedFuturesInfoEntity = IntegratedFuturesInfoEntity.builder()
                .assetCodeEntity(futuresEntity.getAssetCodeEntity())
                .boardIdEntity(futuresEntity.getBoardIdEntity())
                .decimalsEntity(futuresEntity.getDecimalsEntity())
                .futuresEntity(futuresEntity)
                .futuresMarketDataEntity(futuresEntity.getFuturesMarketDataEntity())
                .minStepEntity(futuresEntity.getMinStepEntity())
                .secTypeEntity(futuresEntity.getSecTypeEntity())
                .build();
        return FuturesMapper.integratedEntityToFullSpecification(integratedFuturesInfoEntity);
    }

    public void deleteOldInformation() {
        List<FuturesEntity> oldFutures = futuresRepository.findAllByLastTradeDateBefore(new GregorianCalendar());
        if (!oldFutures.isEmpty()) {
            for (FuturesEntity futuresEntity : oldFutures) {
                Helpers.printAngCarriageGoBack(futuresEntity.getShortName() + " was deleted!");
                deleteTicker(futuresEntity);
            }
            System.out.print("\n");
        }
//        flushAll();
    }

    public boolean isTickerExists(String shortName) {
        return futuresRepository.existsById(shortName);
    }

    public List<String> getAllFuturesShortNames() {
        return futuresRepository.findAllIds();
    }

    public List<FuturesFullSpecification> findAllNearestFutures() {
        List<AssetCodeEntity> assetCodeEntities = assetCodeRepository.findAll();
        List<FuturesFullSpecification> futuresFullSpecifications = new ArrayList<>();
        for (AssetCodeEntity assetCodeEntity : assetCodeEntities) {
            futuresFullSpecifications.add(findNearestFutures(assetCodeEntity));
        }
        return futuresFullSpecifications;

    }

    public FuturesFullSpecification findNearestFutures(AssetCodeEntity assetCodeEntity) {
        List<FuturesEntity> futuresEntities = futuresRepository.findAllByAssetCodeEntity(assetCodeEntity);
        Calendar now = new GregorianCalendar();
        if (!futuresEntities.isEmpty()) {
            long difference;
            Map<Long, FuturesEntity> differences = new HashMap<>();
            for (FuturesEntity futures : futuresEntities) {
                difference = futures.getLastTradeDate().getTimeInMillis() - now.getTimeInMillis();
                if (difference > 0) {
                    differences.put(difference, futures);
                }
            }
            Set<Long> keys = differences.keySet();
            long minDifference = Collections.min(keys);
            FuturesEntity nearestFutures = differences.get(minDifference);
            IntegratedFuturesInfoEntity integratedFuturesInfoEntity = IntegratedFuturesInfoEntity.builder()
                    .assetCodeEntity(nearestFutures.getAssetCodeEntity())
                    .boardIdEntity(nearestFutures.getBoardIdEntity())
                    .decimalsEntity(nearestFutures.getDecimalsEntity())
                    .futuresEntity(nearestFutures)
                    .futuresMarketDataEntity(nearestFutures.getFuturesMarketDataEntity())
                    .minStepEntity(nearestFutures.getMinStepEntity())
                    .secTypeEntity(nearestFutures.getSecTypeEntity())
                    .build();
            return FuturesMapper.integratedEntityToFullSpecification(integratedFuturesInfoEntity);
        }
        return new FuturesFullSpecification();
    }

    public FuturesFullSpecification findNearestFuturesByAssetCode(String assetCode) {
        AssetCodeEntity assetCodeEntity = assetCodeRepository.findById(assetCode).orElse(AssetCodeEntity.builder()
                .assetCode(assetCode)
                .build());
        return findNearestFutures(assetCodeEntity);
    }

    public List<String> getAllAssetCodes() {
        List<AssetCodeEntity> allAssetCodeEntities = assetCodeRepository.findAll();
        List<String> assetCodes = new ArrayList<>();
        for (AssetCodeEntity assetCodeEntity : allAssetCodeEntities) {
            assetCodes.add(assetCodeEntity.getAssetCode());
        }
        return assetCodes;
    }
}
