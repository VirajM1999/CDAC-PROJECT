import { Link } from 'react-router-dom';
import { useState, useEffect } from "react";
import axios from 'axios';

const AdminDashboard = (props) => {

    const loggedInFlag = sessionStorage.getItem("user") != null;
    const user = JSON.parse(sessionStorage.getItem("user"));

    const [orderedCourses, setOrderedCourses] = useState([]);
    const [currentRow, setCurrentRow] = useState([]);

    useEffect(() => {
        let flag = localStorage.getItem("Refresh");
        if (flag == "1") {
            localStorage.setItem("Refresh", "0");
            window.location.reload();
        }

        axios.get(`http://localhost:8080/bitcode/orders/getAllOrders`)
            .then(response => {
                setOrderedCourses(response.data);
            });
    }, [currentRow]);

    var orders = orderedCourses.map(obj => {
        
        return (
            <tr>
                <td>{obj.date}</td>
                <td>{obj.amount}</td>
                <td>{obj.orderStatus}</td>
                <td>
                    {obj.orderStatus=="PENDING"?
                        <button className='approveBtn' onClick={() => {
                        axios.get(`http://localhost:8080/bitcode/orders/approveOrder/${obj.id}`)
                            .then(response => {
                                setCurrentRow(obj);
                                alert("Order status changed successfully.");
                                //setCurrentRow({ ...currentRow, ["orderStatus"]: "APPROVED" });
                                //window.location.reload();
                            });
                        }}>Approve Order</button>:
                        <button className='disabledApproveBtn'>Approve Order</button>
                    }
                </td>
            </tr>
        );
    });

    return (
        <div class="adminContainer">
            <div class="adminSidebar">
                <ol>
                    <li><Link to="/adminDashboard" class="links active">Pending Approvals</Link></li>
                    <li><Link to="/adminDashboardCourse" class="links">Course Stats</Link></li>
                    <li><Link to="/adminDashboardPayments" class="links">Payments History</Link></li>
                    {(loggedInFlag) ?
                        <li><Link to="/logout" class="links">Logout</Link></li> : null
                    }
                </ol>
            </div>
            {(loggedInFlag && user.role == "ROLE_ADMIN") ?
                <div class="adminMainDiv">
                    <div className='purchaseApprovals'>
                        <h3>Orders Pending For Approvals</h3>
                            <table>
                                <tr>
                                    <th>Date</th>
                                    <th>Amount</th>
                                    <th>Status</th>
                                    <th>Change Order Status</th>
                                </tr>
                                {orders}
                            </table>
                    </div>
                </div> :
                <div className='profileMainDiv'>
                    <form className='notLoggedIn'>
                        <h3>You are not logged in as an Admin.</h3>
                        <Link to="/login" class="links">Click here to login</Link>
                    </form>
                </div>
            }
        </div >
    );
}
export default AdminDashboard;