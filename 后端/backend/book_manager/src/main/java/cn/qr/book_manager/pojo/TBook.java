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
 * @TableName t_book
 */
@TableName(value ="t_book")
@Data
public class TBook implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer bookId;

    /**
     * 
     */
    private String bookCover;

    /**
     * 
     */
    private String bookTitle;

    /**
     * 
     */
    private String bookAuthor;

    /**
     * 
     */
    private Date bookDate;

    /**
     * 
     */
    private String bookPress;

    /**
     * 
     */
    private String bookAbs;

    /**
     * 
     */
    private Integer bookCid;

    /**
     * 
     */
    private String bookMp3;

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
        TBook other = (TBook) that;
        return (this.getBookId() == null ? other.getBookId() == null : this.getBookId().equals(other.getBookId()))
            && (this.getBookCover() == null ? other.getBookCover() == null : this.getBookCover().equals(other.getBookCover()))
            && (this.getBookTitle() == null ? other.getBookTitle() == null : this.getBookTitle().equals(other.getBookTitle()))
            && (this.getBookAuthor() == null ? other.getBookAuthor() == null : this.getBookAuthor().equals(other.getBookAuthor()))
            && (this.getBookDate() == null ? other.getBookDate() == null : this.getBookDate().equals(other.getBookDate()))
            && (this.getBookPress() == null ? other.getBookPress() == null : this.getBookPress().equals(other.getBookPress()))
            && (this.getBookAbs() == null ? other.getBookAbs() == null : this.getBookAbs().equals(other.getBookAbs()))
            && (this.getBookCid() == null ? other.getBookCid() == null : this.getBookCid().equals(other.getBookCid()))
            && (this.getBookMp3() == null ? other.getBookMp3() == null : this.getBookMp3().equals(other.getBookMp3()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBookId() == null) ? 0 : getBookId().hashCode());
        result = prime * result + ((getBookCover() == null) ? 0 : getBookCover().hashCode());
        result = prime * result + ((getBookTitle() == null) ? 0 : getBookTitle().hashCode());
        result = prime * result + ((getBookAuthor() == null) ? 0 : getBookAuthor().hashCode());
        result = prime * result + ((getBookDate() == null) ? 0 : getBookDate().hashCode());
        result = prime * result + ((getBookPress() == null) ? 0 : getBookPress().hashCode());
        result = prime * result + ((getBookAbs() == null) ? 0 : getBookAbs().hashCode());
        result = prime * result + ((getBookCid() == null) ? 0 : getBookCid().hashCode());
        result = prime * result + ((getBookMp3() == null) ? 0 : getBookMp3().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bookId=").append(bookId);
        sb.append(", bookCover=").append(bookCover);
        sb.append(", bookTitle=").append(bookTitle);
        sb.append(", bookAuthor=").append(bookAuthor);
        sb.append(", bookDate=").append(bookDate);
        sb.append(", bookPress=").append(bookPress);
        sb.append(", bookAbs=").append(bookAbs);
        sb.append(", bookCid=").append(bookCid);
        sb.append(", bookMp3=").append(bookMp3);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}