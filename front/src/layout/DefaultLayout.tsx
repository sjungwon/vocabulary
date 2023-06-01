import { Outlet } from "react-router-dom";
import { Layout, Row } from "antd";
import Sider from "antd/es/layout/Sider";
import { Content, Footer, Header } from "antd/es/layout/layout";
import MyMenu from "../component/MyMenu";
import Profile from "../component/Profile";
import { useAuth } from "../context/AuthContext";

const DefaultLayout: React.FC = () => {
  const { user } = useAuth();

  return (
    <Layout style={{ height: "100vh", margin: "auto" }}>
      <Sider style={{ backgroundColor: "#030e1c" }} width={225}>
        <Row style={{ height: "20%", borderBottom: ".25rem solid white" }}>
          <Profile data={user} />
        </Row>
        <Row style={{ height: "60%" }}>
          <MyMenu />
        </Row>
        <Row style={{ height: "20%" }}></Row>
      </Sider>
      <Layout>
        <Header style={{ background: "#071c38", height: "5%" }}></Header>
        <Content
          style={{
            background: "#071c38",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <Outlet />
        </Content>
        <Footer style={{ background: "#071c38", height: "5%" }}></Footer>
      </Layout>
    </Layout>
  );
};

export default DefaultLayout;
