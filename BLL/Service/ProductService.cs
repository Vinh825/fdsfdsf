using DAL.Data;
using DAL.Models;
using DAL.Repositories;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BLL.Service
{
    public class ProductService : IProductService
    {
        private readonly IProductRepository _repo;



        public ProductService(IProductRepository repo)
        {
            _repo = repo;
        }
        public async Task AddAsync(Product item)
        {
            await _repo.AddAsync(item);

        }

        public async Task DeleteAsync(object id) => await _repo.DeleteAsync(id);


        public async Task<IEnumerable<Product>> GetAllAsync() => await _repo.GetAllAsync();



        public async Task<Product> GetByIdAsync(object id) => await _repo.GetByIdAsync(id);

        public Task<Product> GetByNameAsync(object name)
        {
            throw new NotImplementedException();
        }


        //public async Task<Product> GetByNameAsync(object name)
        //{
        //    var products = await _repo.GetAllAsync();
        //    return products.FirstOrDefault(p
        //}

        public async Task UpdateAsync(Product item) => await _repo.UpdateAsync(item);

    }

}
