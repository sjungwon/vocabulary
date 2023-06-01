export type PassageType = {
  vocabularyId: number;
  korean: string;
};

export type ProblemType = {
  id: number;
  english: string;
  passages: PassageType[];
};

export type ExamType = {
  id: number;
  problems: ProblemType[];
};

export type AnswerType = {
  problemId: number;
  vocabularyId: number;
};

export type ExamAnswerType = {
  examId: number;
  answers: AnswerType[];
};

type PASSAGE_STATUS = "NONE" | "SELECT";

export type PassageResultType = {
  korean: string;
  status: PASSAGE_STATUS;
};

type PROBLEM_STATUS = "CREATED" | "WRONG" | "CORRECT";

export type ProblemResultType = {
  id: number;
  english: string;
  korean: string;
  status: PROBLEM_STATUS;
  passages: PassageResultType[];
};

export type ExamResultType = {
  id: number;
  problems: ProblemResultType[];
};
