package com.application.WorkManagement.entities.CompositePrimaryKeys;

import com.application.WorkManagement.entities.Account;
import com.application.WorkManagement.entities.TableEntity;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TableCompositeId implements Serializable {

    private Account account;

    private TableEntity table;

}
