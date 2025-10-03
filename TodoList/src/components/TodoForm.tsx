import { useState } from "react";
import { api } from "../lib/api";
import type { CreateTodoPayload, Todo } from "../types";

const today = () => new Date().toISOString().slice(0, 10);

export default function TodoForm() {
  const [title, setTitle] = useState("");
  const [loading, setLoading] = useState(false);
  const [errorMsg, setErrorMsg] = useState<string | null>(null);

  const valid = title.trim().length > 0 && title.trim().length <= 50;

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    if (!valid) return setErrorMsg("제목을 1~50자로 입력해 주세요.");
    setErrorMsg(null);
    setLoading(true);

    const payload: CreateTodoPayload = {
      title: title.trim(),
      completed: false,
      created_at: today(),
    };

    try {
      const { data } = await api.post<Todo>("/api/todos", payload);
      console.log("Created: ", data);
      setTitle("");
      alert("등록 완료!");
    } catch (err: any) {
      console.log(err);
      setErrorMsg(err?.response?.data?.message ?? "등록 실패ㅠㅠ..");
    } finally {
      setLoading(false);
    }
  }

  return (
    <form onSubmit={handleSubmit} className="todo-form">
      <h2>할 일 등록</h2>

      <label>제목</label>
      <input
        placeholder="React 공부"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      ></input>
      <p>등록일: {today()}</p>
      {errorMsg && <p>{errorMsg}</p>}
      <button type="submit" disabled={loading || !valid}>
        {" "}
        {loading ? "등록 중.." : "등록!"}
      </button>
    </form>
  );
}
