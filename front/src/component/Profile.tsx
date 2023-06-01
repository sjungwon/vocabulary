import { Space, Typography } from "antd";
import noProfile from "../image/no-profile.png";
import profileImage from "../image/profile.png";
import { ProfileType } from "../type/member.type";

type Props = {
  data: ProfileType | null;
};

const Profile: React.FC<Props> = ({ data }) => {
  return (
    <>
      <div style={{ display: "flex", alignItems: "center", padding: "1rem" }}>
        <img
          src={data ? profileImage : noProfile}
          alt="profile"
          style={{ width: "40%" }}
        />
        <div
          style={{
            marginLeft: "1rem",
          }}
        >
          <Space direction="vertical">
            <Typography.Paragraph style={{ color: "white", margin: 0 }}>
              {data?.account || "User"}
            </Typography.Paragraph>
            <Typography.Paragraph style={{ color: "white", margin: 0 }}>
              Unknown
            </Typography.Paragraph>
          </Space>
        </div>
      </div>
    </>
  );
};

export default Profile;
