import { Route, Routes } from "react-router-dom";
import DefaultLayout from "./layout/DefaultLayout";
import LoginPage from "./page/LoginPage";
import DefaultPage from "./page/DefaultPage";
import StudyPage from "./page/StudyPage";
import StudyLayout from "./layout/StudyLayout";

function App() {
  return (
    <Routes>
      <Route path="/" element={<DefaultLayout />}>
        <Route path="/" element={<DefaultPage />} />
        <Route path="login" element={<LoginPage />} />
        <Route path="study/*" element={<StudyLayout />}>
          <Route path="" element={<StudyPage.Default />} />
          <Route path="today" element={<StudyPage.Today />} />
        </Route>
      </Route>
    </Routes>
  );
}

export default App;
