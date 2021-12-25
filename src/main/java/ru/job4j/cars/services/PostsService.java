package ru.job4j.cars.services;

import ru.job4j.cars.models.BodyType;
import ru.job4j.cars.models.Brand;
import ru.job4j.cars.models.Photo;
import ru.job4j.cars.models.Post;
import ru.job4j.cars.repositories.CmbPhotosRepository;
import ru.job4j.cars.repositories.HbmBodyTypesRepository;
import ru.job4j.cars.repositories.HbmBrandsRepository;
import ru.job4j.cars.repositories.HbmPostsRepository;
import ru.job4j.cars.repositories.rules.PhotoStore;
import ru.job4j.cars.repositories.rules.PostStore;
import ru.job4j.cars.repositories.rules.Store;

import java.io.InputStream;
import java.util.List;

public class PostsService {

	private static final PostsService INSTANCE = new PostsService();

	private PostsService() { }

	public static PostsService getInstance() {
		return INSTANCE;
	}

	public Post findById(Integer id) {
		PostStore store = HbmPostsRepository.getInstance();
		return store.findById(id);
	}

	public List<Post> findAll() {
		PostStore store = HbmPostsRepository.getInstance();
		return store.findAll();
	}

	public List<Post> findAllOfLastDay() {
		PostStore store = HbmPostsRepository.getInstance();
		return store.findAllOfLastDay();
	}

	public List<Post> findAllWithPhoto() {
		PostStore store = HbmPostsRepository.getInstance();
		return store.findAllWithPhoto();
	}

	public List<Post> findAllByDimensions(
		Boolean isSold, Integer brandId, Integer bodyTypeId, Integer authorId
	) {
		PostStore store = HbmPostsRepository.getInstance();
		return store.findAllByDimensions(isSold, brandId, bodyTypeId, authorId);
	}

	public boolean saveWithPhoto(
		Post value, InputStream photoStream, int bodyTypeId, int brandId
	) {
		PostStore store = HbmPostsRepository.getInstance();
		Store<Integer, BodyType> typeStore = HbmBodyTypesRepository.getInstance();
		Store<Integer, Brand> brandStore = HbmBrandsRepository.getInstance();
		PhotoStore photoStore = CmbPhotosRepository.getInstance();
		BodyType type = typeStore.findById(bodyTypeId);
		Brand brand = brandStore.findById(brandId);
		if (type != null) {
			value.setBodyType(type);
		}
		if (brand != null) {
			value.setCarBrand(brand);
		}
		Photo img = new Photo();
		img.setMimeType("image/*");
		store.save(value);
		img.setId(value.getId());
		if (photoStore.save(img)) {
			photoStore.saveImageFromStream(img, photoStream);
			value.setPhoto(img);
		}
		return store.save(value);
	}

	public boolean save(Post value, int bodyTypeId, int brandId) {
		PostStore store = HbmPostsRepository.getInstance();
		Store<Integer, BodyType> typeStore = HbmBodyTypesRepository.getInstance();
		Store<Integer, Brand> brandStore = HbmBrandsRepository.getInstance();
		BodyType type = typeStore.findById(bodyTypeId);
		Brand brand = brandStore.findById(brandId);
		if (type != null) {
			value.setBodyType(type);
		}
		if (brand != null) {
			value.setCarBrand(brand);
		}
		return store.save(value);
	}

	public boolean deleteById(Integer id) {
		PostStore store = HbmPostsRepository.getInstance();
		return store.deleteById(id);
	}
}
