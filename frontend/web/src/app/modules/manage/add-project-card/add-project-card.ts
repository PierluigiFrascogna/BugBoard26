import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms";
import { ProjectStore } from '../../home/sidebar/sidebar-element/project/project-store';

@Component({
  selector: 'app-add-project-card',
  imports: [ReactiveFormsModule],
  templateUrl: './add-project-card.html',
  styleUrl: './add-project-card.css',
})
export class AddProjectCard {
  private readonly projectStore = inject(ProjectStore);

  projectForm = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(2)])
  })

  createProject(){
    if(this.projectForm.invalid)
      return;
    else
      this.projectStore.createProject(this.projectForm.controls.name.value!);
  }
}
