using DAL.Data;
using DAL.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DAL.Repositories
{
    public class ProductRepository : IProductRepository
    {
        private readonly MVCWebContext _context;
      


        public ProductRepository (MVCWebContext context)
        {
            _context = context;
        }
        public async Task AddAsync(Product item)
        {
            await _context.Product.AddAsync(item);
            await _context.SaveChangesAsync();
        }

        public async Task DeleteAsync(object id)
        {
            var item = await _context.Product.FindAsync(id);
            if (item != null)
            {
                _context.Product.Remove(item);
                await _context.SaveChangesAsync();
            }
        }

        public async Task<IEnumerable<Product>> GetAllAsync()
        {
            return await _context.Product.ToListAsync();
        }

        public async Task<Product> GetByIdAsync(object id)
        {
            return await _context.Product.FindAsync(id);
        }

        public async Task UpdateAsync(Product item)
        {
            _context.Product.Update(item);
            await _context.SaveChangesAsync();
        }
    }

}
