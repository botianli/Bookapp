import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { HotToastService } from '@ngneat/hot-toast';
import { User } from 'src/app/model/user';
import { LoginService } from 'src/app/services/login.service';
import { DomSanitizer,SafeUrl } from '@angular/platform-browser';
export class  ImageSnippet {
  constructor(public src: string, public file: File) {}
}
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  imageSource!:any
  thereIsProfile!:boolean;
  notLoaded=true;
  imageToShow!:any;
  user!:User
  selectedFile!: File;
  imageData!:any;
  //  (change)="processFile(imageInput)"
 
  constructor(private userService:LoginService,private toastService:HotToastService,private domSanitizer:DomSanitizer) { 

  }

  ngOnInit(): void {
    const localUser=localStorage.getItem('user');
    this.user= JSON.parse(localStorage.getItem('user') || '{}');
    this.downLoad();
  }
  success(){
    this.toastService.success("Image uploaded");
  }
  failed(){
  this.toastService.error("fail to upload")  }

  
  onFileChanged(event:any){

    this.selectedFile=event.target.files[0];
  }
  onUpload(){
 
    const uploadData= new FormData();
    uploadData.append('file',this.selectedFile,this.selectedFile.name);
    this.userService.uploadImage(this.user.id,uploadData,this.user.token).subscribe(
      res=>{
        this.success()
        this.downLoad();
      },err=>{
        this.failed();
      }
    )


  }
  
  downLoad(){
    this.userService.downloadImage(this.user.id,this.user.token).subscribe(
    data =>{
      let unsafeImageUrl=URL.createObjectURL(data);
      this.imageSource=this.domSanitizer.bypassSecurityTrustUrl(unsafeImageUrl);
      this.thereIsProfile=true;
      this.notLoaded=false;
      },error=>{
        this.thereIsProfile=false;
      }
    )
  }
 

}
