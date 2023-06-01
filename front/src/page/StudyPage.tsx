import {
  Button,
  Col,
  Divider,
  Input,
  Modal,
  Row,
  Space,
  Typography,
} from "antd";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useCallback, useEffect, useState } from "react";
import QueryString from "qs";
import { VocabularyType } from "../type/vocabulary.type";
import useRepository from "../hook/useRepository";

const PageModal: React.FC = () => {
  const [mState, setMState] = useState<boolean>(false);

  const [size, setSize] = useState<number>(20);

  const [errorMsg, setErrorMsg] = useState<string>("");

  const navigate = useNavigate();

  return (
    <>
      <Button onClick={() => setMState(true)}>오늘의 단어</Button>
      <Modal
        open={mState}
        onOk={() => {
          if (size % 10 !== 0) {
            setErrorMsg("10 단위로 입력해주세요.");
            return;
          }
          if (size > 40) {
            setErrorMsg("40개까지만 가능합니다.");
            return;
          }
          setMState(false);
          navigate(`today?size=${size}`);
          setErrorMsg("");
        }}
        onCancel={() => setMState(false)}
        title="오늘의 단어 암기량"
      >
        <Input
          value={size}
          type="number"
          onChange={(e) => {
            if (e.target.value) {
              setSize(parseInt(e.target.value));
            }
          }}
        />
        <Typography.Paragraph style={{ marginTop: ".5rem" }} type="danger">
          {errorMsg}
        </Typography.Paragraph>
      </Modal>
    </>
  );
};

type VocaElemType = {
  data: VocabularyType;
};

const VocaElem: React.FC<VocaElemType> = ({ data }) => {
  return (
    <Row style={{ width: "100%", maxWidth: "500px", margin: "auto" }}>
      <Col span={11}>
        <Typography.Paragraph style={{ color: "white" }}>
          {data.english}
        </Typography.Paragraph>
      </Col>
      <Col span={2}>
        <Divider type="vertical" />
      </Col>
      <Col span={11}>
        <Typography.Paragraph style={{ color: "white" }}>
          {data.korean}
        </Typography.Paragraph>
      </Col>
    </Row>
  );
};

const Today: React.FC = () => {
  const param = useLocation();

  const { VocabularyRepository } = useRepository();

  const [voca, setVoca] = useState<VocabularyType[]>([]);

  const getData = useCallback(async () => {
    const size = QueryString.parse(param.search.slice(1)).size as
      | string
      | null
      | undefined;

    try {
      const response = await VocabularyRepository.getTodayVoca(
        size && size.length > 0 ? parseInt(size) : 20
      );
      setVoca(response.data);
    } catch (e) {
      window.alert("단어를 가져오는데 실패했습니다.");
    }
  }, [VocabularyRepository, param.search]);

  useEffect(() => {
    getData();
  }, [getData]);

  return (
    <div
      style={{
        width: "100%",
        height: "100%",
        padding: "2rem",
        maxWidth: "1000px",
        margin: "auto",
      }}
    >
      {voca.map((v) => (
        <VocaElem key={v.english} data={v} />
      ))}
    </div>
  );
};

const Wrong: React.FC = () => {
  const { VocabularyRepository } = useRepository();

  const [voca, setVoca] = useState<VocabularyType[]>([]);

  const [message, setMessage] = useState<string>("");

  const getData = useCallback(async () => {
    try {
      const response = await VocabularyRepository.getWrongVoca();
      setVoca(response.data);
      if (response.data.length < 1) {
        setMessage("틀린 단어가 없습니다.");
      }
    } catch (e) {
      window.alert("단어를 가져오는데 실패했습니다.");
    }
  }, [VocabularyRepository]);

  useEffect(() => {
    getData();
  }, [getData]);

  return (
    <div
      style={{
        width: "100%",
        height: "100%",
        padding: "2rem",
        maxWidth: "1000px",
        margin: "auto",
      }}
    >
      {message ? <>{message}</> : null}
      {voca.map((v) => (
        <VocaElem key={v.english} data={v} />
      ))}
    </div>
  );
};

const Default: React.FC = () => {
  return (
    <>
      <div
        style={{
          alignItems: "center",
          marginTop: "5rem",
          display: "flex",
          flexDirection: "column",
        }}
      >
        <Row>
          <Typography.Title style={{ color: "white" }} level={1}>
            Study
          </Typography.Title>
        </Row>
        <Row style={{ marginTop: "2rem" }}>
          <PageModal />
        </Row>
        <Row style={{ marginTop: "2rem" }}>
          <Link to="wrong">
            <Button>틀렸던 단어</Button>
          </Link>
        </Row>
      </div>
    </>
  );
};

const StudyPage = {
  Default,
  Today,
  Wrong,
};

export default StudyPage;
