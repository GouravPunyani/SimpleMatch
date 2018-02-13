import { observable } from 'mobx'
import { Testimonial } from './testimonial'

export class Product {
  @observable id: string
  @observable name: string
  @observable ingredients: string
  @observable description: string
  @observable price: number
  @observable sizeImperial: string
  @observable sizeMetric: string
  @observable testimonials: Array<Testimonial> = []
}
