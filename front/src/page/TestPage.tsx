import React, { useCallback, useEffect, useState } from "react";
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
import { AxiosError, AxiosResponse } from "axios";
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
      <Row style={{ marginBottom: "1rem" }}>
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
    return data;
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

const PrevResult: React.FC = () => {
  const [data, setData] = useState<ExamResultType | null>(null);

  const { TestRepository } = useRepository();

  const [message, setMessage] = useState<string>("");

  const getData = useCallback(async () => {
    try {
      const response = await TestRepository.getPrevResult();
      setData(response.data);
    } catch (e) {
      const err = e as AxiosError;
      console.log(err);
      if (err.response?.status === 404) {
        setMessage("완료된 시험이 없습니다.");
      }
    }
  }, [TestRepository]);

  useEffect(() => {
    getData();
  }, [getData]);

  return (
    <>
      {message ? (
        <div
          style={{
            width: "100%",
            height: "100%",
            padding: "2rem",
            maxWidth: "1000px",
            margin: "auto",
          }}
        >
          {message}
        </div>
      ) : null}
      <Result data={data} />
    </>
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
            Test
          </Typography.Title>
        </Row>
        <Row style={{ marginTop: "2rem" }}>
          <Link to="new">
            <Button>시험 보기</Button>
          </Link>
        </Row>
        <Row style={{ marginTop: "2rem" }}>
          <Link to="prev">
            <Button>이전 결과</Button>
          </Link>
        </Row>
      </div>
    </>
  );
};

const TestPage = {
  Default,
  Test,
  PrevResult,
};

export default TestPage;
