import {Component, OnInit} from '@angular/core';
declare var $: any;
@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  /** 
   * Shows a closable notification on scree.
   * @argument from Top or bottom.
   * @argument align left, right or center
   * @argument message Message to display
   * @argument type info, success, warning or danger
  */
  static showNotification(from, align, message, type) {
    //const type = ['', 'info', 'success', 'warning', 'danger'];

    $.notify({
      icon: "notifications",
      message: message
    }, {
        type: type,
        timer: 4000,
        placement: {
          from: from,
          align: align
        },
        template: '<div data-notify="container" class="col-xl-4 col-lg-4 col-11 col-sm-4 col-md-4 alert alert-{0} alert-with-icon" role="alert">' +
        '<button mat-button  type="button" aria-hidden="true" class="close mat-button" data-notify="dismiss">  <i class="material-icons">close</i></button>' +
        '<i class="material-icons" data-notify="icon">notifications</i> ' +
        '<span data-notify="title">{1}</span> ' +
        '<span data-notify="message">{2}</span>' +
        '<div class="progress" data-notify="progressbar">' +
        '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
        '</div>' +
        '<a href="{3}" target="{4}" data-notify="url"></a>' +
        '</div>'
      });
  }

  constructor() {}

  ngOnInit() {
  }

}