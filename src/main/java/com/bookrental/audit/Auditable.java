package com.bookrental.audit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.bookrental.model.BaseEntityDelete;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable extends BaseEntityDelete {

	@CreatedBy
	@Column(name = "created_by", updatable = false)
	private String createdBy;

	@CreatedDate
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;

	@LastModifiedBy
	@Column(name = "last_modified_by")
	private String lastModifiedBy;

	@LastModifiedDate
	@Column(name = "modified_date", updatable = false)
	private LocalDateTime lastModifiedDate;

	public String getCreatedDate() {
		return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public String getLastModifiedDate() {
		return lastModifiedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

}
