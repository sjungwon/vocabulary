import { useCallback, useEffect, useState } from "react";
import {
  AnswerType,
  ExamResultType,
  ExamType,
  ProblemResultType,
  ProblemType,
} from "../type/exam.type";
import useRepository from "../hook/useRepository";
import { Button, Radio, Row, Space, Tag, Typography } from "antd";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { AxiosResponse } from "axios";
import { ErrorResponse } from "../type/repository.type";

type ProblemProps = {
  data: ProblemType;
  onChange: (vocabularyId: number) => void;
};

const Problem: React.FC<ProblemProps> = ({ data, onChange }) => {
  return (
    <>
      <Row>
        <Typography.Paragraph style={{ color: "white", fontSize: "1rem" }}>
          {data.english}
        </Typography.Paragraph>
      </Row>
      <Row>
        <Radio.Group
          buttonStyle="solid"
          onChange={(e) => {
            const value = e.target.value;
            if (value) {
              onChange(value as number);
            }
          }}
        >
          {data.passages.map((p) => (
            <Radio.Button key={p.vocabularyId} value={p.vocabularyId}>
              {p.korean}
            </Radio.Button>
          ))}
        </Radio.Group>
      </Row>
    </>
  );
};

type ResultElemProps = {
  data: ProblemResultType;
};

const ResultElem: React.FC<ResultElemProps> = ({ data }) => {
  const selected = data.passages.filter((p) => p.status === "SELECT");
  console.log(selected);
  const defaultValue = selected[0]?.korean || data.korean;

  console.log(defaultValue);

  return (
    <>
      <Row>
        <Typography.Paragraph
          style={{ color: "white", fontSize: "1rem", marginBottom: ".5rem" }}
        >
          {data.english}
        </Typography.Paragraph>
      </Row>
      <Row style={{ padding: ".5rem 0" }}>
        <Tag
          color={data.status === "CORRECT" ? "green" : "red"}
        >{`정답: ${data.korean}`}</Tag>
      </Row>
      <Row>
        <Radio.Group
          buttonStyle="solid"
          defaultValue={defaultValue}
          value={defaultValue}
        >
          <Radio.Button key={data.korean} value={data.korean}>
            {data.korean}
          </Radio.Button>
          {data.passages.map((p) => (
            <Radio.Button key={p.korean} value={p.korean}>
              {p.korean}
            </Radio.Button>
          ))}
        </Radio.Group>
      </Row>
    </>
  );
};

type ResultProps = {
  data: ExamResultType | null;
};

const Result: React.FC<ResultProps> = ({ data }) => {
  if (!data) {
    return <>{"로딩중"}</>;
  }
  return (
    <>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          height: "100%",
          padding: "1rem 0",
        }}
      >
        <Space direction="vertical" style={{ margin: "auto" }} size="large">
          {data.problems.map((p) => (
            <ResultElem data={p} key={p.id} />
          ))}
        </Space>
      </div>
    </>
  );
};

type TempAnswer = {
  problemId: number;
  vocabularyId?: number;
};

const Test: React.FC = () => {
  const [data, setData] = useState<ExamType | null>(null);

  const { TestRepository } = useRepository();

  const { status } = useAuth();

  const [answers, setAnswers] = useState<TempAnswer[]>([]);

  const [pageStatus, setPageStatus] = useState<"INIT" | "START" | "END">(
    "INIT"
  );

  const getData = useCallback(async () => {
    if (pageStatus !== "INIT") {
      return;
    }
    try {
      const response = await TestRepository.getTest();
      const data = response.data;
      setData(data);
      setAnswers(
        data.problems.map((p) => ({
          problemId: p.id,
        }))
      );
    } catch (e) {
      if (status === "DONE") {
        window.alert("시험 데이터를 가져오는데 실패했습니다.");
      }
    } finally {
      setPageStatus("START");
    }
  }, [TestRepository, pageStatus, status]);

  useEffect(() => {
    getData();
  }, [getData]);

  const onAnswerChange = (problemId: number) => {
    return (vocabularyId: number) => {
      setAnswers((prev) =>
        prev.map((p) => {
          if (p.problemId !== problemId) {
            return p;
          }

          return {
            ...p,
            vocabularyId,
          };
        })
      );
    };
  };

  const [result, setResult] = useState<ExamResultType | null>(null);

  const submit = async () => {
    if (!data) {
      return;
    }

    const realAnswer = answers
      .filter((a) => a.vocabularyId)
      .map((a) => a as AnswerType);

    if (realAnswer.length !== 10) {
      window.alert("모든 문제를 풀어주세요.");
      console.log(answers);
      return;
    }

    if (!window.confirm("정말 제출하시겠습니까?")) {
      return;
    }
    try {
      const response = await TestRepository.postAnswer({
        examId: data.id,
        answers: realAnswer,
      });
      setPageStatus("END");
      setResult(response.data.data);
    } catch (e) {
      const axiosErr = e as AxiosResponse;
      const errData = axiosErr.data as ErrorResponse;
      if (errData.message) {
        window.alert(errData.message);
      }
    }
  };

  if (pageStatus === "INIT") {
    return <>{"로딩중"}</>;
  }

  if (pageStatus === "START" && data) {
    return (
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          height: "100%",
          padding: "1rem 0",
        }}
      >
        <Space direction="vertical" style={{ margin: "auto" }} size="large">
          {data.problems.map((p) => (
            <Problem data={p} key={p.id} onChange={onAnswerChange(p.id)} />
          ))}
          <Button type="dashed" style={{ margin: "1.5rem 0" }} onClick={submit}>
            제출
          </Button>
        </Space>
      </div>
    );
  }

  return <Result data={result} />;
};

const Default: React.FC = () => {
  return (
    <>
      <Row>
        <Link to="new">시험 보기</Link>
      </Row>
      <Row>
        <Link to="prev">이전 결과</Link>
      </Row>
    </>
  );
};

const TestPage = {
  Default,
  Test,
};

export default TestPage;
