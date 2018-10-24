package com.github.gustavopm1.gotcamel.models.movie;

import com.github.gustavopm1.gotcamel.models.SearchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TypeValueData {
    SearchType type;
    String value;
}
