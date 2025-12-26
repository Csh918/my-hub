package cn.qr.book_manager.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_config
 */
@TableName(value ="t_config")
@Data
public class TConfig implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer configId;

    /**
     * 
     */
    private String configUrl;

    /**
     * 
     */
    private String configTitle;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TConfig other = (TConfig) that;
        return (this.getConfigId() == null ? other.getConfigId() == null : this.getConfigId().equals(other.getConfigId()))
            && (this.getConfigUrl() == null ? other.getConfigUrl() == null : this.getConfigUrl().equals(other.getConfigUrl()))
            && (this.getConfigTitle() == null ? other.getConfigTitle() == null : this.getConfigTitle().equals(other.getConfigTitle()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getConfigId() == null) ? 0 : getConfigId().hashCode());
        result = prime * result + ((getConfigUrl() == null) ? 0 : getConfigUrl().hashCode());
        result = prime * result + ((getConfigTitle() == null) ? 0 : getConfigTitle().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", configId=").append(configId);
        sb.append(", configUrl=").append(configUrl);
        sb.append(", configTitle=").append(configTitle);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}