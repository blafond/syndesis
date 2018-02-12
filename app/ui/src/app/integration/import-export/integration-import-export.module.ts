import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { SyndesisCommonModule, PatternflyUIModule } from '@syndesis/ui/common';

import { IntegrationImportModule } from './import';
import { IntegrationImportExportComponent } from './integration-import-export.component';
import { IntegrationImportComponent } from './import/integration-import.component';

const routes: Routes = [
  {
    path: '',
    component: IntegrationImportExportComponent,
    children: [
      {
        path: 'import',
        component: IntegrationImportComponent
      },
      {
        path: '',
        redirectTo: 'import-export'
      }
    ]
  }
];

@NgModule({
  imports: [
    CommonModule,
    SyndesisCommonModule,
    PatternflyUIModule,
    IntegrationImportModule,
    RouterModule.forChild(routes),
  ],
  exports: [RouterModule],
  declarations: [
    IntegrationImportExportComponent
  ]
})
export class IntegrationImportExportModule { }
