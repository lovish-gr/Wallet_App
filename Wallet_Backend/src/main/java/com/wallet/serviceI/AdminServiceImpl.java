package com.wallet.serviceI;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.wallet.dto.request.AdminReqDto;
import com.wallet.dto.request.CustomerRequestDto;
import com.wallet.dto.response.BulkUploadResponse;
import com.wallet.model.Address;
import com.wallet.model.Admins;
import com.wallet.model.Customer;
import com.wallet.repo.AdminRepo;
import com.wallet.repo.CustomerRepo;
import com.wallet.service.AdminService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
	@Autowired
	AdminRepo adminrepo;
	
	private final CustomerRepo customerRepo;
	private final Validator validator;
	
	@Value("${app.settings.trailDays}") private long trailDays;
	@Override
	public BulkUploadResponse uploadCustomers(MultipartFile file) {
		List<String> successList = new ArrayList<>();
		List<BulkUploadResponse.ErrorRow> errorList = new ArrayList<>();

		try (InputStream inputStream = file.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {

			Sheet sheet = workbook.getSheetAt(0);
			int rowNum = 0;

			for (Row row : sheet) {
				rowNum++;

				if (rowNum == 1)
					continue;

				try {
					CustomerRequestDto dto = mapRowToDto(row);

					StringBuilder errors = new StringBuilder();

					Set<ConstraintViolation<CustomerRequestDto>> violations = validator.validate(dto);
					for (ConstraintViolation<CustomerRequestDto> v : violations) {
						errors.append(v.getPropertyPath()).append(": ").append(v.getMessage()).append("; ");
					}
					if (customerRepo.existsByEmailId(dto.getEmailId())) {
						errors.append("emailId: Email already exists; ");
					}
					if (dto.getPassword() == null || dto.getPassword().length() < 6) {
						errors.append("password: Password must be at least 6 characters; ");
					}
					if (dto.getFirstName() == null || dto.getFirstName().isBlank()) {
						errors.append("firstName: First name cannot be empty; ");
					}
					if (errors.length() > 0) {
						errorList.add(new BulkUploadResponse.ErrorRow(rowNum, errors.toString()));
						continue;
					}

					Customer customer = convertToCustomer(dto);
					customerRepo.save(customer);
					successList.add(dto.getEmailId());

				} catch (Exception e) {
					errorList.add(new BulkUploadResponse.ErrorRow(rowNum, "exception: " + e.getMessage()));
				}
			}
		} catch (Exception e) {
			errorList.add(new BulkUploadResponse.ErrorRow(0, "file processing failed: " + e.getMessage()));
		}

		return new BulkUploadResponse(successList, errorList);
	}

	private CustomerRequestDto mapRowToDto(Row row) {
		CustomerRequestDto dto = new CustomerRequestDto();
		dto.setFirstName(getString(row, 0));
		dto.setLastName(getString(row, 1));
		dto.setEmailId(getString(row, 2));
		dto.setContactNo(getString(row, 3));
		dto.setGender("MALE".equalsIgnoreCase(getString(row, 4)) ? com.wallet.model.Gender.MALE
				: com.wallet.model.Gender.FEMALE);
		dto.setPassword(getString(row, 5));
		dto.setAddressLine1(getString(row, 6));
		dto.setAddressLine2(getString(row, 7));
		dto.setCity(getString(row, 8));
		dto.setState(getString(row, 9));
		dto.setPincode(getString(row, 10));
		return dto;
	}

	private String getString(Row row, int index) {
		Cell cell = row.getCell(index, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue().trim();
	}

	private Customer convertToCustomer(CustomerRequestDto dto) {
		Address address = Address.builder().addressLine1(dto.getAddressLine1()).addressLine2(dto.getAddressLine2())
				.city(dto.getCity()).state(dto.getState()).pincode(dto.getPincode()).build();

		return Customer.builder().firstName(dto.getFirstName()).lastName(dto.getLastName()).emailId(dto.getEmailId())
				.contactNo(dto.getContactNo()).gender(dto.getGender()).password(dto.getPassword()).address(address)
				.registrationDate(LocalDate.now()).lastTrailDate(LocalDate.now().plusDays(trailDays)).build();
	}

	@Override
	public Admins adminAuth(AdminReqDto data) {
		// TODO Auto-generated method stub
		Admins admin = adminrepo.findByEmailAndPass(data.getEmail(), data.getPassword());
		return admin;
	}
}
