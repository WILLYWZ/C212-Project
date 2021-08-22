package model;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static util.Flags.ORDER_FILE;

public class Order extends AbstractReadFile {

    //customer name
    private String cname;
    private List<Ticket> tickets;
    private BigDecimal totalPrice;
    private long ts;


    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    @Override
    public Order readFromString(String s) {
        String[] profiles = s.split(SEPARATOR);
        Order order = new Order();
        order.setCname(profiles[0]);
        String[] ticketArr = profiles[1].split(",");
        List<Ticket> ticketList = new ArrayList<>();
        for (String ticketStr : ticketArr) {
            String[] ticketProfiles = ticketStr.split(SEPARATOR_2);
            for (int i = 0; i < ticketProfiles.length; i++) {
                ticketProfiles[i] = ticketProfiles[i].replace("[", "").replace("]", "");

            }
            Ticket ticket = new Ticket();
            ticket.setMovieName(ticketProfiles[0]);
            ticket.setType(Ticket.Type.valueOf(ticketProfiles[1]));
            ticket.setPrice(new BigDecimal(ticketProfiles[2]));
            ticketList.add(ticket);
        }
        order.setTickets(ticketList);
        order.setTotalPrice(new BigDecimal(profiles[2]));
        return order;
    }

    @Override
    public String toString() {
        return cname + SEPARATOR + tickets.toString() + SEPARATOR + totalPrice + SEPARATOR + ts;
    }

    public static List<Order> listAllOrder() {
        try {
            List<String> all = readFromFile(ORDER_FILE);
            return all.stream().map(per -> {
                return new Order().readFromString(per);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void colPrice() {
        BigDecimal b1 = dealWithWednesday(tickets);
        BigDecimal b2 = dealWithSaturday(tickets);
        BigDecimal b3 = dealWithFamily(tickets);

        this.totalPrice = b1.min(b2).min(b3);

    }

    private BigDecimal dealWithSaturday(List<Ticket> tickets) {
        boolean isSa = LocalDate.now().getDayOfWeek().getValue() == 6;
        BigDecimal result = BigDecimal.ZERO;
        if (isSa) {
            int cou = 0;
            boolean flag = true;
            for (Ticket ticket : tickets) {
                if (!ticket.getType().equals(Ticket.Type.CHILD_MATINEE)) {
                    result =  result.add(ticket.getPrice());
                    continue;
                }
                if (cou % 2 == 0) {
                    result =   result.add(ticket.getPrice());
                }
                ++cou;
            }
            return result;
        }
        for (Ticket ticket : tickets) {
            result =  result.add(ticket.getPrice());
        }
        return result;
    }

    private BigDecimal dealWithWednesday(List<Ticket> tickets) {

        BigDecimal result = BigDecimal.ZERO;
        boolean isW = LocalDate.now().getDayOfWeek().getValue() == 3;
        if (isW) {
            List<Ticket> j = tickets.stream().filter(per -> {
                return Ticket.Type.SENIOR_MATINEE.equals(per.getType());
            }).collect(Collectors.toList());

            for (Ticket ticket : j) {
                result =  result.add(ticket.getPrice());
            }
            return result;
        }
        for (Ticket ticket : tickets) {
            result =   result.add(ticket.getPrice());
        }
        return result;

    }

    private BigDecimal dealWithFamily(List<Ticket> tickets) {

        BigDecimal result = BigDecimal.ZERO;
        int i = 0;
        for (Ticket ticket : tickets) {
            if (ticket.getType().equals(Ticket.Type.ADULT_MATINEE) || ticket.getType().equals(Ticket.Type.ADULT_PRIMETIME)) {
                i++;
            }
        }
        if (i >= 2) {
            for (Ticket ticket : tickets) {
                if (ticket.getType().equals(Ticket.Type.CHILD_MATINEE) || ticket.getType().equals(Ticket.Type.CHILD_PRIMETIME)) {
                   result =  result.add(ticket.getPrice().multiply(new BigDecimal(0.5)));
                   continue;
                }
                result = result.add(ticket.getPrice());
            }
            return result;
        }

        for (Ticket ticket : tickets) {
            result = result.add(ticket.getPrice());
        }
        return result;
    }

    private class TmpTicketAndPrice {
        Ticket ticket;
        BigDecimal price;

        public Ticket getTicket() {
            return ticket;
        }

        public void setTicket(Ticket ticket) {
            this.ticket = ticket;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }
}