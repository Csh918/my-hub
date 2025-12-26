package cn.qr.book_manager.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_history
 */
@TableName(value ="t_history")
@Data
public class THistory implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer historyId;

    /**
     * 
     */
    private Long historyUid;

    /**
     * 
     */
    private Integer historyBid;

    /**
     * 
     */
    private Date historyTime;

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
        THistory other = (THistory) that;
        return (this.getHistoryId() == null ? other.getHistoryId() == null : this.getHistoryId().equals(other.getHistoryId()))
            && (this.getHistoryUid() == null ? other.getHistoryUid() == null : this.getHistoryUid().equals(other.getHistoryUid()))
            && (this.getHistoryBid() == null ? other.getHistoryBid() == null : this.getHistoryBid().equals(other.getHistoryBid()))
            && (this.getHistoryTime() == null ? other.getHistoryTime() == null : this.getHistoryTime().equals(other.getHistoryTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getHistoryId() == null) ? 0 : getHistoryId().hashCode());
        result = prime * result + ((getHistoryUid() == null) ? 0 : getHistoryUid().hashCode());
        result = prime * result + ((getHistoryBid() == null) ? 0 : getHistoryBid().hashCode());
        result = prime * result + ((getHistoryTime() == null) ? 0 : getHistoryTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", historyId=").append(historyId);
        sb.append(", historyUid=").append(historyUid);
        sb.append(", historyBid=").append(historyBid);
        sb.append(", historyTime=").append(historyTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}