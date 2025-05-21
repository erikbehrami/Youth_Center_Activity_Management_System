package services;

import model.Advertisement;
import model.dto.advertisement.CreateAdvertisementDto;
import repository.AdvertisementRepository;

import java.util.ArrayList;

public class AdvertisementService {
    private static final AdvertisementRepository repository = new AdvertisementRepository();

    public static Advertisement create(CreateAdvertisementDto dto) {
        return repository.create(dto);
    }

    public static Advertisement getById(int id) {
        return repository.getById(id);
    }

    public static ArrayList<Advertisement> getAll() {
        return repository.getAll();
    }

    public static boolean delete(int id) {
        return repository.delete(id);
    }
}
