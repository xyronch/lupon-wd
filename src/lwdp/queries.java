/*
 * Programmer: Real Bert Magallanes
 * Email Address: realbertmagallanes@gmail.com
 */
package lwdp;

/**
 *
 * @author civic
 */
import java.sql.*;
import javax.swing.*;

/**
 *
 * @author civic
 */
public class queries extends mySQLconnect {
    private boolean err = false;

    /**
     *
     */
    public queries(){};
    
    /**
     * INSERT OR UPDATE QUERY
     * <p>
     * The {@code InsertUpdatequery} method is allowing this to Insert or
     * Update data in the database
     * </p>
     * Note Only Update and Insert query are allowed by this method.
     *
     * @param   query   the query String that use to insert or update data
     */
    protected void InsertUpdatequery(String query){
       
        try {
            PreparedStatement st = connect().prepareStatement(query);
            st.execute();
            
            this.err = false;
            
        } catch (SQLException e) {
            this.err = true;
            JOptionPane.showMessageDialog(new JPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            disconnect();
        }
    }//END
    
    /**
     *
     * @return
     */
    protected boolean isQueryError(){
        return this.err;
    }
    

    
}
