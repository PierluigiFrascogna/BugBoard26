export interface Issue {
    uuid: string; 
    title: string; 
    description: string; 
    type: "question" | "bug" | "documentation" | "feature";
    priority: "low" | "medium" | "high" | undefined; 
    state: "todo" | "pending" | "done";
    image: string;
    author: string;
}
 