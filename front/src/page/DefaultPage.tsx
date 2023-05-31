import logo from "../image/sangmyung.png";

const DefaultPage: React.FC = () => {
  return (
    <>
      <img
        src={logo}
        alt="상명이미지"
        style={{
          width: "80%",
          margin: "auto",
        }}
      />
    </>
  );
};

export default DefaultPage;
