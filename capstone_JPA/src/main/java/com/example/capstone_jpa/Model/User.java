//package com.example.capstone_jpa.Model;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    @NotEmpty
//    @Size(min = 5)
//    @Column(nullable = false)
//    private String username;
//
//    @NotEmpty
//    @Size(min = 6)
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Must contain letters and digits")
//    @Column(nullable = false)
//    private String password;
//
//    @NotEmpty
//    @Email
//    @Column(nullable = false)
//    private String email;
//
//    @NotEmpty
//    @Pattern(regexp = "^(Admin|Customer)$", message = "Role must be Admin or Customer")
//    @Column(nullable = false)
//    private String role;
//
//    @NotNull
//    @Positive
//    @Column(nullable = false)
//    private Double balance;
//
//    @NotNull
//    private Boolean canRefund = false;
//
//    private Integer lastProductId;
//    private Integer lastMerchantId;
//
//    private Boolean refundRequested = false;
//    private Boolean replaceRequested = false;
//    private Boolean requestApproved = false;
//    private String requestType; // "refund" or "replace"
//    private Integer newProductId; // only if replace
//}

package com.example.capstone_jpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 5)
    @Column(columnDefinition = "varchar(30) not null")
    private String username;

    @NotEmpty
    @Size(min = 6)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Must contain letters and digits")
    @Column(columnDefinition = "varchar(100) not null")
    private String password;

    @NotEmpty
    @Email
    @Column(columnDefinition = "varchar(50) not null")
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(Admin|Customer)$", message = "Role must be Admin or Customer")
    @Column(columnDefinition = "varchar(20) not null")
    private String role;

    @NotNull
    @Positive
    @Column(columnDefinition = "double precision not null")
    private Double balance;

    @NotNull
    @Column(columnDefinition = "boolean not null")
    private Boolean canRefund = false;

    private Integer lastProductId;
    private Integer lastMerchantId;

    @Column(columnDefinition = "boolean default false")
    private Boolean refundRequested = false;

    @Column(columnDefinition = "boolean default false")
    private Boolean replaceRequested = false;

    @Column(columnDefinition = "boolean default false")
    private Boolean requestApproved = false;

    @Column(columnDefinition = "varchar(20)")
    private String requestType; // "refund" or "replace"

    private Integer newProductId; // only if replace
}
