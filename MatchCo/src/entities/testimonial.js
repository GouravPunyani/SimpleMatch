import { observable } from 'mobx'

export class Testimonial {
  @observable id: string
  @observable name: string
  @observable image: ?string
  @observable quote: string
  @observable videoUrl: ?string
}
