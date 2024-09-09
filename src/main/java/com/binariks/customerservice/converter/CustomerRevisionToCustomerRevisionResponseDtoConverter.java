package com.binariks.customerservice.converter;

import com.binariks.customerservice.model.CustomerRevisionResponseDTO;
import com.binariks.customerservice.repository.entity.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.history.Revision;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.binariks.customerservice.util.ConverterUtil.calculateAge;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerRevisionToCustomerRevisionResponseDtoConverter {
    public static CustomerRevisionResponseDTO convert(Revision<Long, Customer> revision) {
        LocalDate dateOfBirth = revision.getEntity().getDateOfBirth().toLocalDateTime().toLocalDate();

        return CustomerRevisionResponseDTO.builder()
                .id(revision.getEntity().getId())
                .name(revision.getEntity().getName())
                .dateOfBirth(dateOfBirth)
                .age(calculateAge(dateOfBirth))
                .address(revision.getEntity().getAddress())
                .gender(revision.getEntity().getGender())
                .revId(((Number) revision.getMetadata().getRequiredRevisionNumber()).longValue())
                .revType(revision.getMetadata().getRevisionType().name())
                .timestamp(LocalDateTime.ofInstant(revision.getMetadata().getRequiredRevisionInstant(), ZoneOffset.UTC))
                .build();
    }
}
