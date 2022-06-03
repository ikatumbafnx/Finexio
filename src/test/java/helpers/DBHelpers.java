/**
 * @author IvanK
 * @email ivan@finexio.com
 * @create date 2022-06-03 15:56:45
 * @modify date 2022-06-03 15:56:45
 * @desc [description]
 */
package helpers;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.finexio.utils.DatabaseUtils;


public class DBHelpers {
    DatabaseUtils dbutils;

    public DBHelpers() {
        dbutils = new DatabaseUtils();
    }

    public List<String> getAccountNumber() {

        ResultSet accountNumbers;
        List<String> results = new ArrayList<>();
        try {

            accountNumbers = dbutils.getDBResult("Select * from Account");
            while (accountNumbers.next()) {
                results.add(accountNumbers.getString("Account_ID"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    public List<String> getValidationUserID(String username) {

        String userID = null;
        ResultSet users;
        List<String> results = new ArrayList<>();

        try {

            users = dbutils.getDBResult(
                    "SELECT u.UserName, m.UserId, u.UserName, p.PatronID, a.autoid AS ApplicationId, a.FirstName, a.LastName, a.SSN "
                            +
                            "FROM aspnet_Membership AS m " +
                            "JOIN aspnet_Users AS u ON u.UserId = m.UserId " +
                            "JOIN Patrons AS p ON p.UserID = m.UserId " +
                            "JOIN Applications AS a ON a.PatronID = p.PatronID " +
                            "WHERE u.username ='" + username + "'");
            while (users.next()) {
                results.add(users.getString("userId"));
                results.add(users.getString("ApplicationId"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;

    }

    public String getUseFinancePlanFlag(String providerID) {

        String flag = null;
        ResultSet users;
        List<String> results = new ArrayList<>();

        try {

            users = dbutils.getDBResult("SELECT Enabled FROM FeatureFlagByProvider WHERE providerid IN (" + providerID
                    + ") AND FeatureName = 'UseFinanceOffers'");
            while (users.next()) {
                flag = users.getString("Enabled");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;

    }

    public String updateUseFinancePlanFlag(String providerID, int flagValue) {

        String flag = null;
        ResultSet rowNumber;

        try {

            rowNumber = dbutils.getDBResult("UPDATE FeatureFlagByProvider SET Enabled =" + flagValue
                    + " WHERE FeatureName = 'UseFinanceOffers' AND ProviderId = " + providerID + "");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;

    }

}
