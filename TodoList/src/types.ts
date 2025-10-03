export interface Todo {
  id: bigint;
  title: string;
  completed: boolean;
  created_at: string;
}

export type CreateTodoPayload = Omit<Todo, "id">;
