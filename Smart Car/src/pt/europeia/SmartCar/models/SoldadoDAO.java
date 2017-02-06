package pt.europeia.SmartCar.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pt.europeia.SmartCar.application.MsgManager;
import pt.europeia.SmartCar.models.Soldier;

public abstract class SoldadoDAO {


	public static Soldier getSoldier(Integer id, String password) {

		Connection con = DBConnector.getConnection();
		if (con != null)
			try {

				PreparedStatement stat = con.prepareStatement("select * from militar where mil_id=? and mil_password=?" );
				stat.setInt(1,id);
				stat.setString(2,password);
				ResultSet rs = stat.executeQuery();
				rs.next();

				Soldier soldier = new Soldier(null, null, null, null, null, 0);

				soldier.setNome(rs.getString("mil_nome"));
				soldier.setEmail(rs.getString("mil_email"));
				soldier.setSalario(rs.getInt("mil_salario"));
				soldier.setDnsc(rs.getString("mil_dnsc"));
				soldier.setPatente(rs.getString("mil_pat_nome"));

				return soldier;

			} catch (SQLException e) {
				
				if (e.getErrorCode() == 0) {
					MsgManager.aviso("BD error", "O ID ou a Password que introduziu n�o est�o correctas", "Introduza um ID e uma Password v�lidas");
				} else {
					MsgManager.aviso("BD error", "Verifique se a BD est� online", "Verifique se a BD est� online");
					System.out.println(e.getErrorCode());
					e.printStackTrace();
				}
			}

			finally {
			try {
				con.close();
			} catch (SQLException e) {
				MsgManager.aviso("BD error", "Erro de chamada � bd", "Verifique se a BD est� online");
				e.printStackTrace();
			}
		}
		else MsgManager.aviso("BD error", "Erro de chamada � bd", "Verifique se a BD est� online");

		return null;


	}

}