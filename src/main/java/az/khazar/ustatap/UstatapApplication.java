package az.khazar.ustatap;

import az.khazar.ustatap.model.Customer;
import az.khazar.ustatap.model.Master;
import az.khazar.ustatap.model.Order;
import az.khazar.ustatap.model.OrderStatus;
import az.khazar.ustatap.model.Review;
import az.khazar.ustatap.repository.CustomerRepository;
import az.khazar.ustatap.repository.MasterRepository;
import az.khazar.ustatap.repository.OrderRepository;
import az.khazar.ustatap.repository.ReviewRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UstatapApplication {

	public static void main(String[] args) {
		SpringApplication.run(UstatapApplication.class, args);
	}

	@Bean
	CommandLineRunner seed(MasterRepository masterRepo, CustomerRepository customerRepo,
						   OrderRepository orderRepo, ReviewRepository reviewRepo) {
		return args -> {
			Master elvin = masterRepo.save(createMaster("Elvin Məmmədov", "elvin@mail.com", "Santexnik", "Nəsimi", 30.0, "10 illik təcrübə"));
			Master resad = masterRepo.save(createMaster("Rəşad Əliyev", "resad@mail.com", "Elektrik", "Yasamal", 25.0, "Ev və ofis elektrik işləri"));
			masterRepo.save(createMaster("Səbinə Hüseynova", "sebine@mail.com", "Təmizlik", "Xətai", 20.0, "Ev və ofis təmizliyi"));
			Master kamran = masterRepo.save(createMaster("Kamran Quliyev", "kamran@mail.com", "Boyaqçı", "Binəqədi", 35.0, "Divar boyama və şpaklyovka"));

			Customer customer = new Customer();
			customer.setFullName("Test Müştəri");
			customer.setEmail("musteri@mail.com");
			customer.setPassword("1234");
			customer.setPhone("055 111 11 11");
			customer.setAddress("Bakı");
			customerRepo.save(customer);

			addReview(orderRepo, reviewRepo, customer, elvin, 5, "Çox sürətli və səliqəli işlədi");
			addReview(orderRepo, reviewRepo, customer, elvin, 4, "Yaxşı iş, qiymət münasibdir");
			addReview(orderRepo, reviewRepo, customer, resad, 5, "Peşəkar elektrik");
			addReview(orderRepo, reviewRepo, customer, kamran, 3, "Normal idi, bir az gecikdi");
		};
	}

	private Master createMaster(String name, String email, String profession,
								String district, double price, String description) {
		Master m = new Master();
		m.setFullName(name);
		m.setEmail(email);
		m.setPassword("1234");
		m.setPhone("050 000 00 00");
		m.setProfession(profession);
		m.setDistrict(district);
		m.setPrice(price);
		m.setDescription(description);
		return m;
	}

	private void addReview(OrderRepository orderRepo, ReviewRepository reviewRepo,
						   Customer customer, Master master, int stars, String comment) {
		Order order = new Order();
		order.setCustomer(customer);
		order.setMaster(master);
		order.setDescription("Nümunə sifariş");
		order.setStatus(OrderStatus.COMPLETED);
		orderRepo.save(order);

		Review review = new Review();
		review.setStars(stars);
		review.setComment(comment);
		review.setOrder(order);
		reviewRepo.save(review);
	}
}