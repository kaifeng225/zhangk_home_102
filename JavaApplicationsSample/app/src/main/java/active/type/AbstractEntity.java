package active.type;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class AbstractEntity implements Serializable {
    @JsonIgnore
    private LocalDateTime createdDt;
    @JsonIgnore
    private String createdBy;
    @JsonIgnore
    private LocalDateTime modifiedDt;
    @JsonIgnore
    private String modifiedBy;
}
