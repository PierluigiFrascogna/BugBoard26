export interface Issue {
    uuid: string; 
    title: string; 
    description: string; 
    type: "question" | "bug" | "documentation" | "feature";
    priority: "low" | "medium" | "high"; 
    state: "todo" | "pending" | "done";
    image: string | null; //TODO: discutere su cosa mandare al frontend e se conviene avere image?: string;
    author: string;
}
 