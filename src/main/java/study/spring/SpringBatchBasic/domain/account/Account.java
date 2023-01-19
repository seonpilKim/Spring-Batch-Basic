package study.spring.SpringBatchBasic.domain.account;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import study.spring.SpringBatchBasic.domain.order.Order;

@Getter
@Entity
@ToString
@Table(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @Id
    private Integer id;
    private String orderItem;
    private Integer price;
    private Date orderDate;
    private Date accountDate;

    public static Account of(Order order) {
        return new Account(order.getId(), order.getOrderItem(), order.getPrice(), order.getOrderDate(), new Date());
    }

}
