import {Auction} from '../../models/auction.interface';

export const catalogue: Auction[] = [{
  'id': 1,
  'title': 'Mona Lisa',
  'itemName': 'Mona Lisa\'s Painting',
  'description': 'One of Da Vinci\'s masterieces',
  'initialValue': 10.99,
  'highestBid': 11,
  'expirationDate': '2021-04-12T07:20:00',
  'imageUrl': 'https://www.everypainterpaintshimself.com/images/made/article_images_new/Mona_Lisa_Large_440_666.jpg',
  'userId': 100,
  'status': 'SOLD',
}];
