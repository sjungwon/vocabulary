import logo from "../image/sangmyung.png";

const DefaultPage: React.FC = () => {
  return (
    <>
      <img
        src={logo}
        alt="상명이미지"
        style={{
          width: "80%",
          maxWidth: "700px",
          maxHeight: "80vh",
          margin: "auto",
        }}
      />
    </>
  );
};

export default DefaultPage;
