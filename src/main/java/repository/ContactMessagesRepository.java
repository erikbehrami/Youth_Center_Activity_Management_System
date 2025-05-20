package repository;

import model.ContactMessages;
import model.dto.contactMessage.CreateContactMessageDto;

import java.sql.*;

public class ContactMessagesRepository extends BaseRepository<ContactMessages, CreateContactMessageDto, Object> {

    public ContactMessagesRepository() {
        super("contactmessages");
    }

    ContactMessages fromResultSet(ResultSet res) throws SQLException {
        return ContactMessages.getInstance(res);
    }

    public ContactMessages create(CreateContactMessageDto createContactMessageDto) {
        String query = "insert into contactmessages (name, email, message, sentAt) values (?, ?, ?, ?)";

        try {
            PreparedStatement pstm =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, createContactMessageDto.getName());
            pstm.setString(2, createContactMessageDto.getEmail());
            pstm.setString(3, createContactMessageDto.getMessage());
            java.util.Date sentAt = createContactMessageDto.getSentAt();
            pstm.setTimestamp(4, new java.sql.Timestamp(sentAt.getTime()));
            pstm.execute();
            ResultSet res = pstm.getGeneratedKeys();
            if (res.next()) {
                int id = res.getInt(1);
                return this.getById(id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ContactMessages update(Object object) {
        return null;
    }
}
