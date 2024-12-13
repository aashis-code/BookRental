package com.bookrental.audit;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {
	
	    @CreatedBy
	    @Column(name = "created_by",updatable = false)
		private String createdBy;
		
	    @CreatedDate
	    @Column(name="created_date", updatable = false)
	    @Temporal(TemporalType.TIMESTAMP)
		private Date createdDate;
		
	    @LastModifiedBy
	    @Column(name="last_modified_by")
		private String lastModifiedBy;
		
		@LastModifiedDate
		@Column(name="modified_date", updatable = false)
		@Temporal(TemporalType.TIMESTAMP)
		private Date lastModifiedDate;

}
